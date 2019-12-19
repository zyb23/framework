package me.zyb.framework.upms.service.impl;


import lombok.extern.slf4j.Slf4j;
import me.zyb.framework.core.util.ListToTreeUtil;
import me.zyb.framework.core.ReturnCode;
import me.zyb.framework.core.ReturnData;
import me.zyb.framework.core.builder.UuidBuilder;
import me.zyb.framework.core.util.StringUtil;
import me.zyb.framework.upms.EntityToModelUtil;
import me.zyb.framework.upms.UpmsException;
import me.zyb.framework.upms.condition.SysUserCondition;
import me.zyb.framework.upms.configure.CustomizedShiroCredentialsMatcher;
import me.zyb.framework.upms.configure.ShiroAuthHelper;
import me.zyb.framework.upms.configure.UpmsProperties;
import me.zyb.framework.upms.dict.PermissionType;
import me.zyb.framework.upms.entity.SysPermission;
import me.zyb.framework.upms.entity.SysRole;
import me.zyb.framework.upms.entity.SysUser;
import me.zyb.framework.upms.entity.SysUserRole;
import me.zyb.framework.upms.model.SysPermissionModel;
import me.zyb.framework.upms.model.SysRoleModel;
import me.zyb.framework.upms.model.SysUserModel;
import me.zyb.framework.upms.repository.SysPermissionRepository;
import me.zyb.framework.upms.repository.SysRoleRepository;
import me.zyb.framework.upms.repository.SysUserRepository;
import me.zyb.framework.upms.repository.SysUserRoleRepository;
import me.zyb.framework.upms.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

;
;

/**
 * @author zhangyingbin
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SysUserServiceImpl implements SysUserService {
	@Autowired
	private UpmsProperties upmsProperties;
	@Autowired
	private SysUserRepository sysUserRepository;
	@Autowired
	private SysRoleRepository sysRoleRepository;
	@Autowired
	private SysUserRoleRepository sysUserRoleRepository;
	@Autowired
	private SysPermissionRepository sysPermissionRepository;

	@Override
	public SysUserModel save(SysUserModel model) {
		SysUser entity = null;
		if(null == model.getId()){
			//新增（注册）
			entity = sysUserRepository.findByLoginName(model.getLoginName());
			if(null != entity){
				throw new UpmsException("登录名已存在");
			}else {
				entity = new SysUser();
				entity.setLoginName(model.getLoginName());
				String salt = UuidBuilder.generateUuid8();
				entity.setSalt(salt);
				String ciphertext = CustomizedShiroCredentialsMatcher.encrypt(model.getLoginPassword(), salt);
				entity.setLoginPassword(ciphertext);
			}
		}else {
			//修改
			Optional<SysUser> optional = sysUserRepository.findById(model.getId());
			if(optional.isPresent()){
				entity = optional.get();
			}else {
				log.error("用户不存在，id：{}", model.getId());
				throw new UpmsException("用户不存在");
			}
		}
		entity.setUsername(model.getUsername());
		entity.setMobile(model.getMobile());
		entity.setEmail(model.getEmail());
		if(null != model.getIsEnable()){
			entity.setIsEnable(model.getIsEnable());
		}

		//设置用户的角色
		List<Long> roleIdList = model.getRoleIdList();
		if(null != roleIdList && roleIdList.size() > 0){
			List<SysRole> roleList = sysRoleRepository.findAllById(model.getRoleIdList());
			entity.setRoleList(roleList);
		}

		sysUserRepository.save(entity);

		BeanUtils.copyProperties(entity, model);

		return model;
	}

	@Override
	public void delete(Long userId) {
		if(SysUser.ADMINISTRATOR_USER_ID.equals(userId)){
			log.warn("超级管理员用户不允许删除");
		}else {
			//删除用户（用户角色中间表会自动级联删除）
			sysUserRepository.deleteById(userId);
		}
	}

	@Override
	public void updateLoginPassword(Long userId, String loginPassword) {
		Optional<SysUser> optional = sysUserRepository.findById(userId);
		if(optional.isPresent()){
			SysUser entity = optional.get();
			String ciphertext = CustomizedShiroCredentialsMatcher.encrypt(loginPassword, entity.getSalt());
			entity.setLoginPassword(ciphertext);
			sysUserRepository.save(entity);
		}else {
			log.error("用户不存在，id：{}", userId);
		}
	}

	/**
	 * 构造查询规则
	 * @param condition 查询条件
	 * @return Specification<SysUser>
	 */
	private Specification<SysUser> buildSpecification(SysUserCondition condition){
		return (Specification<SysUser>) (root, query, criteriaBuilder) -> {
			List<Predicate> predicateList = new ArrayList<Predicate>();
			if(null != condition.getId()){
				predicateList.add(criteriaBuilder.equal(root.get("id").as(Long.class), condition.getId()));
			}
			if(StringUtils.isNotBlank(condition.getLoginName())){
				predicateList.add(criteriaBuilder.like(root.get("loginName").as(String.class), StringUtil.like(condition.getLoginName())));
			}
			if(StringUtils.isNotBlank(condition.getUsername())){
				predicateList.add(criteriaBuilder.like(root.get("username").as(String.class), StringUtil.like(condition.getUsername())));
			}
			if(StringUtils.isNotBlank(condition.getMobile())){
				predicateList.add(criteriaBuilder.like(root.get("mobile").as(String.class), StringUtil.like(condition.getMobile())));
			}
			if(StringUtils.isNotBlank(condition.getEmail())){
				predicateList.add(criteriaBuilder.like(root.get("email").as(String.class), StringUtil.like(condition.getEmail())));
			}
			if(null != condition.getIsDisable()){
				predicateList.add(criteriaBuilder.equal(root.get("isDisable").as(Boolean.class), condition.getIsDisable()));
			}
			if (null != condition.getRoleIdList() && condition.getRoleIdList().size() > 0){
				Join<SysUser, SysRole> role = root.join(root.getModel().getList("roleList", SysRole.class));
				CriteriaBuilder.In<Long> in = criteriaBuilder.in(role.get("id"));
				for (Long roleId : condition.getRoleIdList()){
					in.value(roleId);
				}
				predicateList.add(in);
			}
			query.where(predicateList.toArray(new Predicate[predicateList.size()]));
			return query.getRestriction();
		};
	}

	@Override
	public List<SysUserModel> queryAll(){
		List<SysUser> entityList = sysUserRepository.findAll();
		return EntityToModelUtil.entityToModel(entityList, false);
	}

	private Page<SysUser> findByCondition(SysUserCondition condition) {
		return sysUserRepository.findAll(buildSpecification(condition), condition.getPageable());
	}

	@Override
	public Page<SysUserModel> queryByCondition(SysUserCondition condition) {
		Page<SysUser> entityPage = findByCondition(condition);
		List<SysUser> entityList = entityPage.getContent();
		List<SysUserModel> modelList = EntityToModelUtil.entityToModel(entityList, true);
		return new PageImpl<>(modelList, entityPage.getPageable(), entityPage.getTotalElements());
	}

	@Override
	public SysUserModel queryByLoginName(String loginName) {
		SysUser entity = sysUserRepository.findByLoginName(loginName);
		return EntityToModelUtil.entityToModel(entity);
	}

	@Override
	public void saveRole(Long userId, List<Long> roleIdList){
		//userId是否存在
		Optional<SysUser> optional = sysUserRepository.findById(userId);
		if(optional.isPresent()){
			SysUser sysUser = optional.get();
			List<SysRole> sysRoleList = sysRoleRepository.findAllById(roleIdList);
			sysUser.setRoleList(sysRoleList);
			sysUserRepository.save(sysUser);
		}else{
			log.error("用户不存在：{}", userId);
		}
	}

	/**
	 * <p>更新用户角色（中间表）</p>
	 * <p>根据roleIdList判断</p>
	 * <p>1：用户已有roleIdList中的角色，不作任何操作</p>
	 * <p>2：用户没有roleIdList中的角色，新增</p>
	 * <p>3：用户现有角色不在roleIdList中，删除</p>
	 * @param userId        用户ID
	 * @param roleIdList    角色ID列表
	 */
	private void saveUserRole(Long userId, List<Long> roleIdList){
		//查询用户角色中间表
		List<SysUserRole> sysUserRoleList = sysUserRoleRepository.findByUserIdAndRoleIdIn(userId, roleIdList);
		//查询用户已有的角色
		List<Long> alreadyHaveRoleIdList = sysUserRoleList.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());

		//用户需要新增的角色
		List<Long> addRoleIdList = roleIdList.stream().filter(roleId -> !alreadyHaveRoleIdList.contains(roleId)).collect(Collectors.toList());
		addSysUserRole(userId, addRoleIdList);
		//用户需要删除的角色
		List<Long> delRoleIdList = alreadyHaveRoleIdList.stream().filter(roleId -> !roleIdList.contains(roleId)).collect(Collectors.toList());
		deleteSysUserRole(userId, delRoleIdList);
	}

	/**
	 * 新增用户角色（中间表）
	 * @param userId        用户ID
	 * @param roleIdList    角色ID列表
	 * @return List<SysUserRole>
	 */
	private List<SysUserRole> addSysUserRole(Long userId, List<Long> roleIdList){
		List<SysUserRole> sysUserRoleList = new ArrayList<SysUserRole>();
		for(Long roleId : roleIdList){
			SysUserRole sysUserRole = new SysUserRole();
			sysUserRole.setUserId(userId);
			sysUserRole.setRoleId(roleId);

			sysUserRoleList.add(sysUserRole);
		}
		sysUserRoleList = sysUserRoleRepository.saveAll(sysUserRoleList);

		return sysUserRoleList;
	}

	@Override
	public void deleteSysUserRole(Long userId, List<Long> roleIdList){
		sysUserRoleRepository.deleteByUserIdAndRoleIdIn(userId, roleIdList);
	}

	@Override
	public List<SysRoleModel> queryRole(Long userId) {
		List<SysRole> roleEntityList = sysRoleRepository.findByUserList_Id(userId);
		return EntityToModelUtil.entityToModel4Role(roleEntityList);
	}

	@Override
	public List<SysPermissionModel> queryPermission(Long userId, Long parentPermissionId) {
		List<SysPermission> permissionEntityList;
		if(null == parentPermissionId){
			permissionEntityList = sysPermissionRepository.findByRoleList_UserList_Id(userId);
		}else {
			permissionEntityList = sysPermissionRepository.findByParent_IdAndRoleList_UserList_Id(parentPermissionId, userId);
		}
		return EntityToModelUtil.entityToModel4Permission(permissionEntityList);
	}

	@Override
	public List<SysPermissionModel> queryTopPermission(Long userId) {
		List<SysPermissionModel> permissionModelList = queryPermission(userId, null);
		List<SysPermissionModel> topPermissionList = permissionModelList.stream().filter(permissionModel -> SysPermission.TOP_PARENT_ID.equals(permissionModel.getParentId())).collect(Collectors.toList());
		return topPermissionList;
	}

	@Override
	public List<SysPermissionModel> queryPermissionTree(Long userId, Long parentPermissionId, PermissionType permissionType) {
		List<SysPermissionModel> permissionModelList = queryPermission(userId, parentPermissionId);
		return listToTree(permissionModelList, permissionType);
	}

	/**
	 * 用户权限列表转化为树形
	 * @param permissionModelList   用户所有权限
	 * @param permissionType        权限类型（为空时，所有类型）
	 * @return List<AdminRightModel>
	 */
	private List<SysPermissionModel> listToTree(List<SysPermissionModel> permissionModelList, PermissionType permissionType){
		if(null == permissionType){
			return ListToTreeUtil.listToTree(permissionModelList, SysPermissionModel.class);
		}else {
			List<SysPermissionModel> newList = permissionModelList.stream().filter(permissionModel -> permissionModel.getType().equals(permissionType)).collect(Collectors.toList());
			return ListToTreeUtil.listToTree(newList, SysPermissionModel.class);
		}
	}

	@Override
	public ReturnData login(String loginName, String loginPassword) {
		//用户身份校验
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(loginName, loginPassword);
		ReturnData returnData;
		String message;
		try {
			subject.login(token);
			subject.getSession().setTimeout(upmsProperties.getSessionTimeOut());
			returnData = new ReturnData(ReturnCode.SUCCESS.getValue(), "成功");
		} catch (UnknownAccountException | IncorrectCredentialsException e) {
			message = "用户名/密码错误";
			log.error(message);
			returnData = new ReturnData(ReturnCode.FAILURE.getValue(), message);
		} catch (LockedAccountException e) {
			message = "该用户被锁定";
			log.error(message);
			returnData = new ReturnData(ReturnCode.FAILURE.getValue(), message);
		} catch (DisabledAccountException e){
			message = "该用户被冻结";
			log.error(message);
			returnData = new ReturnData(ReturnCode.FAILURE.getValue(), message);
		} catch (AuthenticationException e) {
			message = "用户不存在";
			log.error(message);
			returnData = new ReturnData(ReturnCode.FAILURE.getValue(), message);
		} catch (Exception e) {
			log.error("登录异常", e);
			returnData = new ReturnData(ReturnCode.FAILURE.getValue(), "登录失败");
		}

		SysUser userEntity = ShiroAuthHelper.getCurrentSysUser();
		//返回Data
		SysUserModel userModel = EntityToModelUtil.entityToModel(userEntity, true);

		//获取登录用户的所有角色数据
		List<SysRoleModel> roleModelList = queryRole(userEntity.getId());
		userModel.setRoleIdList(roleModelList.stream().map(SysRoleModel::getId).collect(Collectors.toList()));

		//获取登录用户的所有权限（列表形式）
		List<SysPermissionModel> permissionModelList = queryPermission(userEntity.getId(), null);
//		userModel.setPermissionList(permissionModelList);
		//获取登录用户的所有权限编码
		Set<String> permissionCodeList = permissionModelList.stream().map(SysPermissionModel::getCode).collect(Collectors.toSet());
		userModel.setPermissionCodeList(permissionCodeList);
		//获取登录用户的所有菜单（树形）
		List<SysPermissionModel> topTree = queryPermissionTree(userEntity.getId(), null, null);
		userModel.setPermissionTree(topTree);

		returnData.setData(userModel);

		return returnData;
	}

	@Override
	public void logout() {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
	}

	@Override
	public List<SysUserModel> queryByRoleId(Long roleId) {
		List<SysUser> entityList = sysUserRepository.findByRoleList_Id(roleId);
		return EntityToModelUtil.entityToModel4User(entityList);
	}

	@Override
	public List<SysUserModel> queryByIdList(List<Long> idList) {
		List<SysUser> entityList = sysUserRepository.findAllById(idList);
		return EntityToModelUtil.entityToModel4User(entityList);
	}

	@Override
	public void lockById(Long id) {
		//TODO
	}

	@Override
	public void unlockById(Long id) {
		//TODO
	}

	@Override
	public void freezeById(Long id) {
		sysUserRepository.freezeById(id);
	}

	@Override
	public void unfreezeById(Long id) {
		sysUserRepository.unfreezeById(id);
	}

	@Override
	public void freezeUnfreezeById(Long userId, boolean isEnable) {
		if(isEnable) {
			unfreezeById(userId);
		}else {
			freezeById(userId);
		}
	}
}
