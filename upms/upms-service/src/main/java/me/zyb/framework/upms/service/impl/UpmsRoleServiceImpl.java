package me.zyb.framework.upms.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.zyb.framework.core.util.StringUtil;
import me.zyb.framework.core.util.regex.StringRegex;
import me.zyb.framework.upms.EntityToModelUtil;
import me.zyb.framework.upms.UpmsException;
import me.zyb.framework.upms.condition.UpmsRoleCondition;
import me.zyb.framework.upms.entity.UpmsPermission;
import me.zyb.framework.upms.entity.UpmsRole;
import me.zyb.framework.upms.entity.UpmsRolePermission;
import me.zyb.framework.upms.model.UpmsPermissionModel;
import me.zyb.framework.upms.model.UpmsRoleModel;
import me.zyb.framework.upms.repository.UpmsPermissionRepository;
import me.zyb.framework.upms.repository.UpmsRolePermissionRepository;
import me.zyb.framework.upms.repository.UpmsRoleRepository;
import me.zyb.framework.upms.repository.UpmsUserRoleRepository;
import me.zyb.framework.upms.service.UpmsPermissionService;
import me.zyb.framework.upms.service.UpmsRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author zhangyingbin
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UpmsRoleServiceImpl implements UpmsRoleService {
	@Autowired
	private UpmsRoleRepository upmsRoleRepository;
	@Autowired
	private UpmsPermissionRepository upmsPermissionRepository;
	@Autowired
	private UpmsRolePermissionRepository upmsRolePermissionRepository;
	@Autowired
	private UpmsUserRoleRepository upmsUserRoleRepository;

	@Autowired
	private UpmsPermissionService upmsPermissionService;

	@Override
	public UpmsRoleModel save(UpmsRoleModel model){
		UpmsRole entity = null;
		if(null == model.getId()){
			//新增
			if(!StringRegex.isAlphabetOrNumber(model.getCode())){
				throw new UpmsException("角色编码只能是英文或数字");
			}
			entity = upmsRoleRepository.findByCode(model.getName());
			if(null != entity){
				throw new UpmsException("角色编码已存在");
			}else {
				entity = new UpmsRole();
				entity.setCode(model.getCode());
			}
		}else {
			//修改
			Optional<UpmsRole> optional = upmsRoleRepository.findById(model.getId());
			if(optional.isPresent()){
				entity = optional.get();
			}else {
				throw new UpmsException("角色不存在");
			}
		}
		entity.setName(model.getName());
		entity.setDescription(model.getDescription());

		//设置角色的权限
		List<Long> permissionIdList = model.getPermissionIdList();
		if(null != permissionIdList && permissionIdList.size() > 0){
			List<UpmsPermission> permissionList = upmsPermissionRepository.findAllById(permissionIdList);
			entity.setPermissionList(permissionList);
		}

		upmsRoleRepository.save(entity);

		BeanUtils.copyProperties(entity, model);

		return model;
	}

	@Override
	public void delete(Long roleId) {
		if(UpmsRole.ADMINISTRATOR_ROLE_ID.equals(roleId)){
			log.warn("超级管理员角色不允许删除");
			return;
		}else {
			//先删除用户角色关系
			upmsUserRoleRepository.deleteByRoleId(roleId);
			//再删除角色（角色权限中间表会自动级联删除）
			upmsRoleRepository.deleteById(roleId);
		}
	}

	@Override
	public List<UpmsRoleModel> queryAll() {
		List<UpmsRole> entityList = upmsRoleRepository.findAll();
		return EntityToModelUtil.entityToModel(entityList, false, false, false);
	}

	/**
	 * 构造查询规则
	 * @param condition 查询条件
	 * @return Specification<UpmsRole>
	 */
	private Specification<UpmsRole> buildSpecification(UpmsRoleCondition condition){
		return (Specification<UpmsRole>) (root, query, criteriaBuilder) -> {
			List<Predicate> predicateList = new ArrayList<Predicate>();
			if(null != condition.getId()){
				predicateList.add(criteriaBuilder.equal(root.get("id").as(Long.class), condition.getId()));
			}
			if(StringUtils.isNotBlank(condition.getName())){
				predicateList.add(criteriaBuilder.like(root.get("name").as(String.class), StringUtil.like(condition.getName())));
			}
			query.where(predicateList.toArray(new Predicate[predicateList.size()]));
			return query.getRestriction();
		};
	}

	private Page<UpmsRole> findByCondition(UpmsRoleCondition condition){
		return upmsRoleRepository.findAll(buildSpecification(condition), condition.getPageable());
	}

	@Override
	public Page<UpmsRoleModel> queryByCondition(UpmsRoleCondition condition){
		Page<UpmsRole> entityPage = findByCondition(condition);
		List<UpmsRole> entityList = entityPage.getContent();
		List<UpmsRoleModel> modelList = EntityToModelUtil.entityToModel(entityList, condition.getNeedPermissionList(), condition.getNeedPermissionIdList(), condition.getNeedPermissionCodeList());
		return new PageImpl<UpmsRoleModel>(modelList, entityPage.getPageable(), entityPage.getTotalElements());
	}

	@Override
	public List<UpmsRoleModel> queryByIdList(List<Long> idList) {
		List<UpmsRole> entityList = upmsRoleRepository.findAllById(idList);
		return EntityToModelUtil.entityToModel4Role(entityList);
	}

	@Override
	public List<UpmsRoleModel> queryByUserId(Long userId){
		List<UpmsRole> entityList = upmsRoleRepository.findByUserList_Id(userId);
		return EntityToModelUtil.entityToModel4Role(entityList);
	}

	@Override
	public List<UpmsRoleModel> queryByPermissionId(Long permissionId) {
		List<UpmsRole> entityList =  upmsRoleRepository.findByPermissionList_Id(permissionId);
		return EntityToModelUtil.entityToModel4Role(entityList);
	}

	@Override
	public void savePermission(Long roleId, List<Long> permissionIdList) {
		//roleId是否存在
		Optional<UpmsRole> optional = upmsRoleRepository.findById(roleId);
		if(optional.isPresent()){
			UpmsRole upmsRole = optional.get();
			List<UpmsPermission> upmsPermissionList = upmsPermissionRepository.findAllById(permissionIdList);
			upmsRole.setPermissionList(upmsPermissionList);
			upmsRoleRepository.save(upmsRole);
		}else{
			log.error("角色不存在，id：{}", roleId);
		}
	}

	/**
	 * <p>更新角色权限（中间表）</p>
	 * <p>根据permissionIdList判断</p>
	 * <p>1：用户已有permissionIdList中的角色，不作任何操作</p>
	 * <p>2：用户没有permissionIdList中的角色，新增</p>
	 * <p>3：用户现有角色不在permissionIdList中，删除</p>
	 * @param roleId            用户ID
	 * @param permissionIdList  角色ID列表
	 */
	private void saveRolePermission(Long roleId, List<Long> permissionIdList){
		//查询角色权限中间表
		List<UpmsRolePermission> upmsRolePermissionList = upmsRolePermissionRepository.findByRoleIdAndPermissionIdIn(roleId, permissionIdList);
		//查询角色已有的权限
		List<Long> alreadyHavePermissionIdList = upmsRolePermissionList.stream().map(UpmsRolePermission::getPermissionId).collect(Collectors.toList());

		//角色需要新增的权限
		List<Long> addPermissionIdList = permissionIdList.stream().filter(permissionId -> !alreadyHavePermissionIdList.contains(permissionId)).collect(Collectors.toList());
		addUpmsRolePermission(roleId, addPermissionIdList);
		//角色需要删除的权限
		List<Long> delPermissionIdList = alreadyHavePermissionIdList.stream().filter(permissionId -> !permissionIdList.contains(permissionId)).collect(Collectors.toList());
		deleteUpmsRolePermission(roleId, delPermissionIdList);
	}

	/**
	 * 新增角色权限（中间表）
	 * @param roleId            角色ID
	 * @param permissionIdList  权限ID列表
	 * @return List<UpmsRolePermission>
	 */
	public List<UpmsRolePermission> addUpmsRolePermission(Long roleId, List<Long> permissionIdList){
		List<UpmsRolePermission> upmsRolePermissionList = new ArrayList<UpmsRolePermission>();
		for(Long permissionId : permissionIdList){
			UpmsRolePermission upmsRolePermission = new UpmsRolePermission();
			upmsRolePermission.setRoleId(roleId);
			upmsRolePermission.setPermissionId(permissionId);

			upmsRolePermissionList.add(upmsRolePermission);
		}
		upmsRolePermissionList = upmsRolePermissionRepository.saveAll(upmsRolePermissionList);

		return upmsRolePermissionList;
	}

	@Override
	public void deleteUpmsRolePermission(Long roleId, List<Long> permissionIdList){
		upmsRolePermissionRepository.deleteByRoleIdAndPermissionIdIn(roleId, permissionIdList);
	}

	@Override
	public List<UpmsPermissionModel> queryPermission(Long roleId) {
		List<UpmsPermission> permissionEntityList = upmsPermissionRepository.findByRoleList_Id(roleId);
		return EntityToModelUtil.entityToModel4Permission(permissionEntityList);
	}

	@Override
	public UpmsRoleModel queryDetail(Long id) {
		Optional<UpmsRole> optional = upmsRoleRepository.findById(id);
		if(optional.isPresent()){
			UpmsRole roleEntity = optional.get();
			UpmsRoleModel model = EntityToModelUtil.entityToModel(roleEntity, false, true, true);
			List<UpmsPermissionModel> permissionTree = upmsPermissionService.queryTree(null, false, true);
			markHave(model.getPermissionIdList(), permissionTree);
			model.setPermissionTree(permissionTree);
			return model;
		}
		return null;
	}

	/**
	 * 在所有权限的树形结构中，给角色拥有的权限打上标识
	 * @param havePermissionIdList  角色拥有的所有权限ID列表
	 * @param permissionTree        所有权限的树形结构
	 * @return List<UpmsPermissionModel>
	 */
	private List<UpmsPermissionModel> markHave(List<Long> havePermissionIdList, List<UpmsPermissionModel> permissionTree){
		for(Long havePermissionId : havePermissionIdList){
			markHave(havePermissionId, permissionTree);
		}
		return permissionTree;
	}

	/**
	 * 在所有权限的树形结构中，给角色拥有的权限打上标识
	 * @param havePermissionId  角色拥有的权限ID
	 * @param permissionTree    树形所有权限
	 * @return List<AdminRightModel>
	 */
	private List<UpmsPermissionModel> markHave(Long havePermissionId, List<UpmsPermissionModel> permissionTree){
		for (UpmsPermissionModel permission : permissionTree){
			if(permission.getId().equals(havePermissionId)){
				permission.setHave(true);
			}else {
				List<UpmsPermissionModel> children = permission.getChildren();
				if(null != children && children.size() > 0){
					markHave(havePermissionId, children);
				}
			}
		}
		return permissionTree;
	}
}
