package me.zyb.framework.upms.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.zyb.framework.core.util.StringUtil;
import me.zyb.framework.upms.EntityToModelUtil;
import me.zyb.framework.upms.UpmsException;
import me.zyb.framework.upms.condition.SysRoleCondition;
import me.zyb.framework.upms.entity.SysPermission;
import me.zyb.framework.upms.entity.SysRole;
import me.zyb.framework.upms.entity.SysRolePermission;
import me.zyb.framework.upms.model.SysPermissionModel;
import me.zyb.framework.upms.model.SysRoleModel;
import me.zyb.framework.upms.repository.SysPermissionRepository;
import me.zyb.framework.upms.repository.SysRolePermissionRepository;
import me.zyb.framework.upms.repository.SysRoleRepository;
import me.zyb.framework.upms.repository.SysUserRoleRepository;
import me.zyb.framework.upms.service.SysPermissionService;
import me.zyb.framework.upms.service.SysRoleService;
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
public class SysRoleServiceImpl implements SysRoleService {
	@Autowired
	private SysRoleRepository sysRoleRepository;
	@Autowired
	private SysPermissionRepository sysPermissionRepository;
	@Autowired
	private SysRolePermissionRepository sysRolePermissionRepository;
	@Autowired
	private SysUserRoleRepository sysUserRoleRepository;

	@Autowired
	private SysPermissionService sysPermissionService;

	@Override
	public SysRoleModel save(SysRoleModel model){
		SysRole entity = null;
		if(null == model.getId()){
			//新增
			entity = sysRoleRepository.findByName(model.getName());
			if(null != entity){
				throw new UpmsException("角色名已存在");
			}else {
				entity = new SysRole();
				entity.setName(model.getName());
			}
		}else {
			//修改
			Optional<SysRole> optional = sysRoleRepository.findById(model.getId());
			if(optional.isPresent()){
				entity = optional.get();
			}else {
				throw new UpmsException("角色不存在");
			}
		}
		entity.setDescription(model.getDescription());

		//设置角色的权限
		List<Long> permissionIdList = model.getPermissionIdList();
		if(null != permissionIdList && permissionIdList.size() > 0){
			List<SysPermission> permissionList = sysPermissionRepository.findAllById(permissionIdList);
			entity.setPermissionList(permissionList);
		}

		sysRoleRepository.save(entity);

		BeanUtils.copyProperties(entity, model);

		return model;
	}

	@Override
	public void delete(Long roleId) {
		if(SysRole.ADMINISTRATOR_ROLE_ID.equals(roleId)){
			log.warn("超级管理员角色不允许删除");
			return;
		}else {
			//先删除用户角色关系
			sysUserRoleRepository.deleteByRoleId(roleId);
			//再删除角色（角色权限中间表会自动级联删除）
			sysRoleRepository.deleteById(roleId);
		}
	}

	@Override
	public List<SysRoleModel> queryAll() {
		List<SysRole> entityList = sysRoleRepository.findAll();
		return EntityToModelUtil.entityToModel(entityList, false, false, false);
	}

	/**
	 * 构造查询规则
	 * @param condition 查询条件
	 * @return Specification<SysRole>
	 */
	private Specification<SysRole> buildSpecification(SysRoleCondition condition){
		return (Specification<SysRole>) (root, query, criteriaBuilder) -> {
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

	private Page<SysRole> findByCondition(SysRoleCondition condition){
		return sysRoleRepository.findAll(buildSpecification(condition), condition.getPageable());
	}

	@Override
	public Page<SysRoleModel> queryByCondition(SysRoleCondition condition){
		Page<SysRole> entityPage = findByCondition(condition);
		List<SysRole> entityList = entityPage.getContent();
		List<SysRoleModel> modelList = EntityToModelUtil.entityToModel(entityList, condition.getNeedPermissionList(), condition.getNeedPermissionIdList(), condition.getNeedPermissionCodeList());
		return new PageImpl<SysRoleModel>(modelList, entityPage.getPageable(), entityPage.getTotalElements());
	}

	@Override
	public List<SysRoleModel> queryByIdList(List<Long> idList) {
		List<SysRole> entityList = sysRoleRepository.findAllById(idList);
		return EntityToModelUtil.entityToModel4Role(entityList);
	}

	@Override
	public List<SysRoleModel> queryByUserId(Long userId){
		List<SysRole> entityList = sysRoleRepository.findByUserList_Id(userId);
		return EntityToModelUtil.entityToModel4Role(entityList);
	}

	@Override
	public List<SysRoleModel> queryByPermissionId(Long permissionId) {
		List<SysRole> entityList =  sysRoleRepository.findByPermissionList_Id(permissionId);
		return EntityToModelUtil.entityToModel4Role(entityList);
	}

	@Override
	public void savePermission(Long roleId, List<Long> permissionIdList) {
		//roleId是否存在
		Optional<SysRole> optional = sysRoleRepository.findById(roleId);
		if(optional.isPresent()){
			SysRole sysRole = optional.get();
			List<SysPermission> sysPermissionList = sysPermissionRepository.findAllById(permissionIdList);
			sysRole.setPermissionList(sysPermissionList);
			sysRoleRepository.save(sysRole);
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
		List<SysRolePermission> sysRolePermissionList = sysRolePermissionRepository.findByRoleIdAndPermissionIdIn(roleId, permissionIdList);
		//查询角色已有的权限
		List<Long> alreadyHavePermissionIdList = sysRolePermissionList.stream().map(SysRolePermission::getPermissionId).collect(Collectors.toList());

		//角色需要新增的权限
		List<Long> addPermissionIdList = permissionIdList.stream().filter(permissionId -> !alreadyHavePermissionIdList.contains(permissionId)).collect(Collectors.toList());
		addSysRolePermission(roleId, addPermissionIdList);
		//角色需要删除的权限
		List<Long> delPermissionIdList = alreadyHavePermissionIdList.stream().filter(permissionId -> !permissionIdList.contains(permissionId)).collect(Collectors.toList());
		deleteSysRolePermission(roleId, delPermissionIdList);
	}

	/**
	 * 新增角色权限（中间表）
	 * @param roleId            角色ID
	 * @param permissionIdList  权限ID列表
	 * @return List<SysRolePermission>
	 */
	public List<SysRolePermission> addSysRolePermission(Long roleId, List<Long> permissionIdList){
		List<SysRolePermission> sysRolePermissionList = new ArrayList<SysRolePermission>();
		for(Long permissionId : permissionIdList){
			SysRolePermission sysRolePermission = new SysRolePermission();
			sysRolePermission.setRoleId(roleId);
			sysRolePermission.setPermissionId(permissionId);

			sysRolePermissionList.add(sysRolePermission);
		}
		sysRolePermissionList = sysRolePermissionRepository.saveAll(sysRolePermissionList);

		return sysRolePermissionList;
	}

	@Override
	public void deleteSysRolePermission(Long roleId, List<Long> permissionIdList){
		sysRolePermissionRepository.deleteByRoleIdAndPermissionIdIn(roleId, permissionIdList);
	}

	@Override
	public List<SysPermissionModel> queryPermission(Long roleId) {
		List<SysPermission> permissionEntityList = sysPermissionRepository.findByRoleList_Id(roleId);
		return EntityToModelUtil.entityToModel4Permission(permissionEntityList);
	}

	@Override
	public SysRoleModel queryDetail(Long id) {
		Optional<SysRole> optional = sysRoleRepository.findById(id);
		if(optional.isPresent()){
			SysRole roleEntity = optional.get();
			SysRoleModel model = EntityToModelUtil.entityToModel(roleEntity, false, true, true);
			List<SysPermissionModel> permissionTree = sysPermissionService.queryTree(null, false, true);
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
	 * @return List<SysPermissionModel>
	 */
	private List<SysPermissionModel> markHave(List<Long> havePermissionIdList, List<SysPermissionModel> permissionTree){
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
	private List<SysPermissionModel> markHave(Long havePermissionId, List<SysPermissionModel> permissionTree){
		for (SysPermissionModel permission : permissionTree){
			if(permission.getId().equals(havePermissionId)){
				permission.setHave(true);
			}else {
				List<SysPermissionModel> children = permission.getChildren();
				if(null != children && children.size() > 0){
					markHave(havePermissionId, children);
				}
			}
		}
		return permissionTree;
	}
}
