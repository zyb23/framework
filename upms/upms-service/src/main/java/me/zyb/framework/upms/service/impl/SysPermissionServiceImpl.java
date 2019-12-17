package me.zyb.framework.upms.service.impl;


import lombok.extern.slf4j.Slf4j;
import me.zyb.framework.core.util.StringUtil;
import me.zyb.framework.upms.EntityToModelUtil;
import me.zyb.framework.upms.UpmsException;
import me.zyb.framework.upms.condition.SysPermissionCondition;
import me.zyb.framework.upms.entity.SysPermission;
import me.zyb.framework.upms.model.SysPermissionModel;
import me.zyb.framework.upms.repository.SysPermissionRepository;
import me.zyb.framework.upms.service.SysPermissionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author zhangyingbin
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SysPermissionServiceImpl implements SysPermissionService {
	@Autowired
	private SysPermissionRepository sysPermissionRepository;

	@Override
	public SysPermissionModel save(SysPermissionModel model) {
		SysPermission entity = null;
		if(null == model.getId()){
			//新增
			entity = sysPermissionRepository.findByCode(model.getCode());
			if(null != entity){
				throw new UpmsException("权限编码已存在");
			}else {
				entity = new SysPermission();
				entity.setParent(null != model.getParentId() ? model.getParentId() : SysPermission.TOP_PARENT_ID);
				entity.setCode(model.getCode());
				entity.setType(model.getType());
			}
		}else {
			//修改
			Optional<SysPermission> optional = sysPermissionRepository.findById(model.getId());
			if(optional.isPresent()){
				entity = optional.get();
			}else {
				throw new UpmsException("权限不存在");
			}
		}
		entity.setName(model.getName());
		entity.setAction(model.getAction());
		entity.setRoute(model.getRoute());
		entity.setDescription(model.getDescription());
		entity.setSort(model.getSort());

		sysPermissionRepository.save(entity);

		BeanUtils.copyProperties(entity, model);

		return model;
	}

	@Override
	public void delete(Long permissionId) {
		//是否有子级
		List<SysPermission> children = sysPermissionRepository.findByParent_Id(permissionId);
		if(null != children && children.size() > 0){
			throw new UpmsException("拥有子级的权限不能删除");
		}else {
			sysPermissionRepository.deleteById(permissionId);
		}
	}

	@Override
	public List<SysPermissionModel> queryByParentId(Long parentId) {
		List<SysPermission> entityList = sysPermissionRepository.findByParent_Id(parentId);
		return EntityToModelUtil.entityToModel4Permission(entityList);
	}

	@Override
	public List<SysPermissionModel> queryAll(){
		List<SysPermission> entityList = sysPermissionRepository.findAll();
		return EntityToModelUtil.entityToModel4Permission(entityList);
	}

	/**
	 * 构造查询规则
	 * @param condition 查询条件
	 * @return Specification<SysPermission>
	 */
	private Specification<SysPermission> buildSpecification(SysPermissionCondition condition){
		return (Specification<SysPermission>) (root, query, criteriaBuilder) -> {
			List<Predicate> predicateList = new ArrayList<Predicate>();
			if(null != condition.getId()){
				predicateList.add(criteriaBuilder.equal(root.get("id").as(Long.class), condition.getId()));
			}
			if(StringUtils.isNotBlank(condition.getCode())){
				predicateList.add(criteriaBuilder.like(root.get("code").as(String.class), StringUtil.like(condition.getCode())));
			}
			if(StringUtils.isNotBlank(condition.getName())){
				predicateList.add(criteriaBuilder.like(root.get("name").as(String.class), StringUtil.like(condition.getName())));
			}
			if (null != condition.getParentId()){
				Join<SysPermission, SysPermission> parent = root.join("parent");
				predicateList.add(criteriaBuilder.equal(parent.get("id").as(Long.class), condition.getParentId()));
			}
			query.where(predicateList.toArray(new Predicate[predicateList.size()]));
			return query.getRestriction();
		};
	}

	private Page<SysPermission> findByCondition(SysPermissionCondition condition){
		return sysPermissionRepository.findAll(buildSpecification(condition), condition.getPageable());
	}

	@Override
	public Page<SysPermissionModel> queryByCondition(SysPermissionCondition condition) {
		Page<SysPermission> entityPage = findByCondition(condition);
		List<SysPermission> entityList = entityPage.getContent();
		List<SysPermissionModel> modelList = EntityToModelUtil.entityToModel(entityList, condition.getNeedParent(), condition.getNeedChildren());
		return new PageImpl<SysPermissionModel>(modelList, entityPage.getPageable(), entityPage.getTotalElements());
	}

	@Override
	public List<SysPermissionModel> queryByIdList(List<Long> idList) {
		List<SysPermission> entityList = sysPermissionRepository.findAllById(idList);
		return EntityToModelUtil.entityToModel4Permission(entityList);
	}

	@Override
	public List<SysPermissionModel> queryByRoleId(Long roleId) {
		List<SysPermission> entityList = sysPermissionRepository.findByRoleList_Id(roleId);
		return EntityToModelUtil.entityToModel4Permission(entityList);
	}

	@Override
	public List<SysPermissionModel> queryTree(Long parentId, boolean needParent, boolean needChildren) {
		List<SysPermission> adminRightList = sysPermissionRepository.findByParent_IdOrderBySortAsc(parentId);
		return EntityToModelUtil.entityToModel(adminRightList, needParent, needChildren);
	}
}
