package me.zyb.framework.upms.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.zyb.framework.core.util.StringUtil;
import me.zyb.framework.core.util.regex.StringRegex;
import me.zyb.framework.upms.EntityToModelUtil;
import me.zyb.framework.upms.UpmsException;
import me.zyb.framework.upms.condition.UpmsPermissionCondition;
import me.zyb.framework.upms.dict.PermissionType;
import me.zyb.framework.upms.entity.UpmsPermission;
import me.zyb.framework.upms.entity.UpmsRole;
import me.zyb.framework.upms.model.UpmsPermissionModel;
import me.zyb.framework.upms.repository.UpmsPermissionRepository;
import me.zyb.framework.upms.repository.UpmsRoleRepository;
import me.zyb.framework.upms.service.UpmsPermissionService;
import org.apache.commons.lang3.StringUtils;
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
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zhangyingbin
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UpmsPermissionServiceImpl implements UpmsPermissionService {
	@Autowired
	private UpmsPermissionRepository upmsPermissionRepository;
	@Autowired
	private UpmsRoleRepository upmsRoleRepository;

	@Override
	public UpmsPermissionModel save(UpmsPermissionModel model) {
		UpmsPermission entity = null;
		if(null == model.getId()){
			//新增
			if(!StringRegex.isAlphabetOrNumber(model.getCode())){
				throw new UpmsException("权限编码只能是英文或数字");
			}
			entity = upmsPermissionRepository.findByCode(model.getCode());
			if(null != entity){
				throw new UpmsException("权限编码已存在");
			}else {
				entity = new UpmsPermission();
				entity.setCode(model.getCode());
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
		if(null != model.getParentId()) {
			Optional<UpmsPermission> parent = upmsPermissionRepository.findById(model.getParentId());
			if(parent.isPresent()) {
				entity.setParent(parent.get());
				entity.setLevel(parent.get().getLevel() + 1);
			}
		}
		entity.setType(model.getType());
		entity.setName(model.getName());
		entity.setAction(model.getAction());
		entity.setIcon(model.getIcon());
		entity.setRoute(model.getRoute());
		entity.setDescription(model.getDescription());
		entity.setSort(model.getSort());

		upmsPermissionRepository.save(entity);

		model = EntityToModelUtil.entityToModel(entity);

		return model;
	}

	@Override
	public void delete(Long permissionId) {
		//是否有子级
		List<UpmsPermission> children = upmsPermissionRepository.findByParent_Id(permissionId);
		if(null != children && children.size() > 0){
			throw new UpmsException("拥有子级的权限不能删除");
		}
		//是否被角色绑定
		List<UpmsRole> roleList = upmsRoleRepository.findByPermissionList_Id(permissionId);
		if(null != roleList && roleList.size() > 0) {
			Set<Long> roleIdSet = roleList.stream().map(UpmsRole::getId).collect(Collectors.toSet());
			throw new UpmsException("权限被角色" + roleIdSet.toString() + "绑定，不能删除");
		}

		upmsPermissionRepository.deleteById(permissionId);
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
			if (null != condition.getType()){
				predicateList.add(criteriaBuilder.equal(root.get("type").as(PermissionType.class), condition.getType()));
			}
			if (null != condition.getLevel()){
				predicateList.add(criteriaBuilder.equal(root.get("level").as(Integer.class), condition.getLevel()));
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
		List<UpmsPermissionModel> modelList = EntityToModelUtil.entityToModel4Permission(entityList, condition.getNeedParent(), condition.getNeedChildren());
		return new PageImpl<UpmsPermissionModel>(modelList, entityPage.getPageable(), entityPage.getTotalElements());
	}

	@Override
	public List<UpmsPermissionModel> queryByIdSet(Set<Long> idSet) {
		List<UpmsPermission> entityList = upmsPermissionRepository.findAllById(idSet);
		return EntityToModelUtil.entityToModel4Permission(entityList);
	}

	@Override
	public List<UpmsPermissionModel> queryByRoleId(Long roleId) {
		List<UpmsPermission> entityList = upmsPermissionRepository.findByRoleList_Id(roleId);
		return EntityToModelUtil.entityToModel4Permission(entityList);
	}

	@Override
	public List<UpmsPermissionModel> queryTree(Long parentId, boolean needParent, boolean needChildren) {
		List<UpmsPermission> upmsPermissionList = upmsPermissionRepository.findByParent_IdOrderBySortAsc(parentId);
		return EntityToModelUtil.entityToModel4Permission(upmsPermissionList, needParent, needChildren);
	}
}
