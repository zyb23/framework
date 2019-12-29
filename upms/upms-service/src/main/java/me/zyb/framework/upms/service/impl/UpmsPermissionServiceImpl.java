package me.zyb.framework.upms.service.impl;


import lombok.extern.slf4j.Slf4j;
import me.zyb.framework.core.util.StringUtil;
import me.zyb.framework.upms.EntityToModelUtil;
import me.zyb.framework.upms.UpmsException;
import me.zyb.framework.upms.condition.UpmsPermissionCondition;
import me.zyb.framework.upms.entity.UpmsPermission;
import me.zyb.framework.upms.model.UpmsPermissionModel;
import me.zyb.framework.upms.repository.UpmsPermissionRepository;
import me.zyb.framework.upms.service.UpmsPermissionService;
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
public class UpmsPermissionServiceImpl implements UpmsPermissionService {
	@Autowired
	private UpmsPermissionRepository upmsPermissionRepository;

	@Override
	public UpmsPermissionModel save(UpmsPermissionModel model) {
		UpmsPermission entity = null;
		if(null == model.getId()){
			//新增
			entity = upmsPermissionRepository.findByCode(model.getCode());
			if(null != entity){
				throw new UpmsException("权限编码已存在");
			}else {
				entity = new UpmsPermission();
				entity.setParent(null != model.getParentId() ? model.getParentId() : UpmsPermission.TOP_PARENT_ID);
				entity.setCode(model.getCode());
				entity.setType(model.getType());
			}
		}else {
			//修改
			Optional<UpmsPermission> optional = upmsPermissionRepository.findById(model.getId());
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

		upmsPermissionRepository.save(entity);

		BeanUtils.copyProperties(entity, model);

		return model;
	}

	@Override
	public void delete(Long permissionId) {
		//是否有子级
		List<UpmsPermission> children = upmsPermissionRepository.findByParent_Id(permissionId);
		if(null != children && children.size() > 0){
			throw new UpmsException("拥有子级的权限不能删除");
		}else {
			upmsPermissionRepository.deleteById(permissionId);
		}
	}

	@Override
	public List<UpmsPermissionModel> queryByParentId(Long parentId) {
		List<UpmsPermission> entityList = upmsPermissionRepository.findByParent_Id(parentId);
		return EntityToModelUtil.entityToModel4Permission(entityList);
	}

	@Override
	public List<UpmsPermissionModel> queryAll(){
		List<UpmsPermission> entityList = upmsPermissionRepository.findAll();
		return EntityToModelUtil.entityToModel4Permission(entityList);
	}

	/**
	 * 构造查询规则
	 * @param condition 查询条件
	 * @return Specification<UpmsPermission>
	 */
	private Specification<UpmsPermission> buildSpecification(UpmsPermissionCondition condition){
		return (Specification<UpmsPermission>) (root, query, criteriaBuilder) -> {
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
				Join<UpmsPermission, UpmsPermission> parent = root.join("parent");
				predicateList.add(criteriaBuilder.equal(parent.get("id").as(Long.class), condition.getParentId()));
			}
			query.where(predicateList.toArray(new Predicate[predicateList.size()]));
			return query.getRestriction();
		};
	}

	private Page<UpmsPermission> findByCondition(UpmsPermissionCondition condition){
		return upmsPermissionRepository.findAll(buildSpecification(condition), condition.getPageable());
	}

	@Override
	public Page<UpmsPermissionModel> queryByCondition(UpmsPermissionCondition condition) {
		Page<UpmsPermission> entityPage = findByCondition(condition);
		List<UpmsPermission> entityList = entityPage.getContent();
		List<UpmsPermissionModel> modelList = EntityToModelUtil.entityToModel(entityList, condition.getNeedParent(), condition.getNeedChildren());
		return new PageImpl<UpmsPermissionModel>(modelList, entityPage.getPageable(), entityPage.getTotalElements());
	}

	@Override
	public List<UpmsPermissionModel> queryByIdList(List<Long> idList) {
		List<UpmsPermission> entityList = upmsPermissionRepository.findAllById(idList);
		return EntityToModelUtil.entityToModel4Permission(entityList);
	}

	@Override
	public List<UpmsPermissionModel> queryByRoleId(Long roleId) {
		List<UpmsPermission> entityList = upmsPermissionRepository.findByRoleList_Id(roleId);
		return EntityToModelUtil.entityToModel4Permission(entityList);
	}

	@Override
	public List<UpmsPermissionModel> queryTree(Long parentId, boolean needParent, boolean needChildren) {
		List<UpmsPermission> adminRightList = upmsPermissionRepository.findByParent_IdOrderBySortAsc(parentId);
		return EntityToModelUtil.entityToModel(adminRightList, needParent, needChildren);
	}
}