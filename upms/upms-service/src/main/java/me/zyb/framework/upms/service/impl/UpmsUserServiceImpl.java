package me.zyb.framework.upms.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.zyb.framework.core.ReturnCode;
import me.zyb.framework.core.builder.UuidBuilder;
import me.zyb.framework.core.util.ListToTreeUtil;
import me.zyb.framework.core.util.StringUtil;
import me.zyb.framework.upms.EntityToModelUtil;
import me.zyb.framework.upms.UpmsException;
import me.zyb.framework.upms.condition.UpmsUserCondition;
import me.zyb.framework.upms.configure.CustomizedShiroCredentialsMatcher;
import me.zyb.framework.upms.configure.ShiroAuthHelper;
import me.zyb.framework.upms.configure.UpmsProperties;
import me.zyb.framework.upms.dict.PermissionType;
import me.zyb.framework.upms.entity.UpmsPermission;
import me.zyb.framework.upms.entity.UpmsRole;
import me.zyb.framework.upms.entity.UpmsUser;
import me.zyb.framework.upms.entity.UpmsUserRole;
import me.zyb.framework.upms.model.UpmsPermissionModel;
import me.zyb.framework.upms.model.UpmsRoleModel;
import me.zyb.framework.upms.model.UpmsUserModel;
import me.zyb.framework.upms.repository.UpmsPermissionRepository;
import me.zyb.framework.upms.repository.UpmsRoleRepository;
import me.zyb.framework.upms.repository.UpmsUserRepository;
import me.zyb.framework.upms.repository.UpmsUserRoleRepository;
import me.zyb.framework.upms.service.UpmsUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
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


/**
 * @author zhangyingbin
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UpmsUserServiceImpl implements UpmsUserService {
	@Autowired
	private UpmsProperties upmsProperties;
	@Autowired
	private UpmsUserRepository upmsUserRepository;
	@Autowired
	private UpmsRoleRepository upmsRoleRepository;
	@Autowired
	private UpmsUserRoleRepository upmsUserRoleRepository;
	@Autowired
	private UpmsPermissionRepository upmsPermissionRepository;

	@Override
	public UpmsUserModel save(UpmsUserModel model) {
		UpmsUser entity = null;
		if(null == model.getId()){
			//新增（注册）
			entity = upmsUserRepository.findByLoginName(model.getLoginName());
			if(null != entity){
				throw new UpmsException("登录名已存在");
			}else {
				entity = new UpmsUser();
				entity.setLoginName(model.getLoginName());
				String salt = UuidBuilder.generateUuid8();
				entity.setSalt(salt);
				String ciphertext = CustomizedShiroCredentialsMatcher.encrypt(model.getLoginPassword(), salt);
				entity.setLoginPassword(ciphertext);
			}
		}else {
			//修改
			Optional<UpmsUser> optional = upmsUserRepository.findById(model.getId());
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
		Set<Long> roleIdSet = model.getRoleIdSet();
		if(null != roleIdSet && roleIdSet.size() > 0){
			List<UpmsRole> roleList = upmsRoleRepository.findAllById(model.getRoleIdSet());
			entity.setRoleList(roleList);
		}

		upmsUserRepository.save(entity);

		model = EntityToModelUtil.entityToModel(entity);

		return model;
	}

	@Override
	public void delete(Long userId) {
		if(UpmsUser.ADMINISTRATOR_USER_ID.equals(userId)){
			log.warn("超级管理员用户不允许删除");
		}else {
			//删除用户（用户角色中间表会自动级联删除）
			upmsUserRepository.deleteById(userId);
		}
	}

	@Override
	public void updateLoginPassword(Long userId, String loginPassword) {
		Optional<UpmsUser> optional = upmsUserRepository.findById(userId);
		if(optional.isPresent()){
			UpmsUser entity = optional.get();
			String ciphertext = CustomizedShiroCredentialsMatcher.encrypt(loginPassword, entity.getSalt());
			entity.setLoginPassword(ciphertext);
			upmsUserRepository.save(entity);
		}else {
			log.error("用户不存在，id：{}", userId);
		}
	}

	/**
	 * 构造查询规则
	 * @param condition 查询条件
	 * @return Specification<UpmsUser>
	 */
	private Specification<UpmsUser> buildSpecification(UpmsUserCondition condition){
		return (Specification<UpmsUser>) (root, query, criteriaBuilder) -> {
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
			if(null != condition.getIsEnable()){
				predicateList.add(criteriaBuilder.equal(root.get("isEnable").as(Boolean.class), condition.getIsEnable()));
			}
			if (null != condition.getRoleIdSet() && condition.getRoleIdSet().size() > 0){
				Join<UpmsUser, UpmsRole> role = root.join(root.getModel().getList("roleList", UpmsRole.class));
				CriteriaBuilder.In<Long> in = criteriaBuilder.in(role.get("id"));
				for (Long roleId : condition.getRoleIdSet()){
					in.value(roleId);
				}
				predicateList.add(in);
			}
			query.where(predicateList.toArray(new Predicate[predicateList.size()]));
			return query.getRestriction();
		};
	}

	@Override
	public List<UpmsUserModel> queryAll(){
		List<UpmsUser> entityList = upmsUserRepository.findAll();
		return EntityToModelUtil.entityToModel(entityList, false);
	}

	private Page<UpmsUser> findByCondition(UpmsUserCondition condition) {
		return upmsUserRepository.findAll(buildSpecification(condition), condition.getPageable());
	}

	@Override
	public Page<UpmsUserModel> queryByCondition(UpmsUserCondition condition) {
		Page<UpmsUser> entityPage = findByCondition(condition);
		List<UpmsUser> entityList = entityPage.getContent();
		List<UpmsUserModel> modelList = EntityToModelUtil.entityToModel(entityList, true);
		return new PageImpl<>(modelList, entityPage.getPageable(), entityPage.getTotalElements());
	}

	@Override
	public UpmsUserModel queryByLoginName(String loginName) {
		UpmsUser entity = upmsUserRepository.findByLoginName(loginName);
		return EntityToModelUtil.entityToModel(entity);
	}

	@Override
	public void saveRole(Long userId, Set<Long> roleIdSet){
		//userId是否存在
		Optional<UpmsUser> optional = upmsUserRepository.findById(userId);
		if(optional.isPresent()){
			UpmsUser upmsUser = optional.get();
			List<UpmsRole> upmsRoleList = upmsRoleRepository.findAllById(roleIdSet);
			upmsUser.setRoleList(upmsRoleList);
			upmsUserRepository.save(upmsUser);
		}else{
			log.error("用户不存在：{}", userId);
		}
	}

	/**
	 * <pre>
	 *     更新用户角色（中间表）
	 *     根据roleIdSet判断
	 *     1：用户已有roleIdSet中的角色，不作任何操作
	 *     2：用户没有roleIdSet中的角色，新增
	 *     3：用户现有角色不在roleIdSet中，删除
	 * @param userId        用户ID
	 * @param roleIdSet     角色ID列表
	 */
	private void saveUserRole(Long userId, Set<Long> roleIdSet){
		//查询用户角色中间表
		List<UpmsUserRole> upmsUserRoleList = upmsUserRoleRepository.findByUserIdAndRoleIdIn(userId, roleIdSet);
		//查询用户已有的角色
		Set<Long> alreadyHaveRoleIdSet = upmsUserRoleList.stream().map(UpmsUserRole::getRoleId).collect(Collectors.toSet());

		//用户需要新增的角色
		Set<Long> addRoleIdSet = roleIdSet.stream().filter(roleId -> !alreadyHaveRoleIdSet.contains(roleId)).collect(Collectors.toSet());
		addUpmsUserRole(userId, addRoleIdSet);
		//用户需要删除的角色
		Set<Long> delRoleIdSet = alreadyHaveRoleIdSet.stream().filter(roleId -> !roleIdSet.contains(roleId)).collect(Collectors.toSet());
		deleteUpmsUserRole(userId, delRoleIdSet);
	}

	/**
	 * 新增用户角色（中间表）
	 * @param userId        用户ID
	 * @param roleIdSet     角色ID列表
	 * @return List<UpmsUserRole>
	 */
	private List<UpmsUserRole> addUpmsUserRole(Long userId, Set<Long> roleIdSet){
		List<UpmsUserRole> upmsUserRoleList = new ArrayList<UpmsUserRole>();
		for(Long roleId : roleIdSet){
			UpmsUserRole upmsUserRole = new UpmsUserRole();
			upmsUserRole.setUserId(userId);
			upmsUserRole.setRoleId(roleId);

			upmsUserRoleList.add(upmsUserRole);
		}
		upmsUserRoleList = upmsUserRoleRepository.saveAll(upmsUserRoleList);

		return upmsUserRoleList;
	}

	@Override
	public void deleteUpmsUserRole(Long userId, Set<Long> roleIdSet){
		upmsUserRoleRepository.deleteByUserIdAndRoleIdIn(userId, roleIdSet);
	}

	@Override
	public List<UpmsRoleModel> queryRole(Long userId) {
		List<UpmsRole> roleEntityList = upmsRoleRepository.findByUserList_Id(userId);
		return EntityToModelUtil.entityToModel4Role(roleEntityList);
	}

	@Override
	public List<UpmsPermissionModel> queryPermission(Long userId, Long parentPermissionId) {
		List<UpmsPermission> permissionEntityList;
		if(null == parentPermissionId){
			permissionEntityList = upmsPermissionRepository.findByRoleList_UserList_Id(userId);
		}else {
			permissionEntityList = upmsPermissionRepository.findByParent_IdAndRoleList_UserList_Id(parentPermissionId, userId);
		}
		return EntityToModelUtil.entityToModel4Permission(permissionEntityList);
	}

	@Override
	public List<UpmsPermissionModel> queryTopPermission(Long userId) {
		List<UpmsPermissionModel> permissionModelList = queryPermission(userId, null);
		List<UpmsPermissionModel> topPermissionList = permissionModelList.stream().filter(permissionModel -> UpmsPermission.TOP_PARENT_ID.equals(permissionModel.getParentId())).collect(Collectors.toList());
		return topPermissionList;
	}

	@Override
	public List<UpmsPermissionModel> queryPermissionTree(Long userId, Long parentPermissionId, PermissionType permissionType) {
		List<UpmsPermissionModel> permissionModelList = queryPermission(userId, parentPermissionId);
		return listToTree(permissionModelList, permissionType);
	}

	/**
	 * 用户权限列表转化为树形
	 * @param permissionModelList   用户所有权限
	 * @param permissionType        权限类型（为空时，所有类型）
	 * @return List<AdminRightModel>
	 */
	private List<UpmsPermissionModel> listToTree(List<UpmsPermissionModel> permissionModelList, PermissionType permissionType){
		if(null == permissionType){
			return ListToTreeUtil.listToTree(permissionModelList, UpmsPermissionModel.class);
		}else {
			List<UpmsPermissionModel> newList = permissionModelList.stream().filter(permissionModel -> permissionModel.getType().equals(permissionType)).collect(Collectors.toList());
			return ListToTreeUtil.listToTree(newList, UpmsPermissionModel.class);
		}
	}

	@Override
	public UpmsUserModel login(String loginName, String loginPassword) {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		try {
			UsernamePasswordToken token = new UsernamePasswordToken(loginName, loginPassword);
			subject.login(token);
		} catch (UnknownAccountException | IncorrectCredentialsException e) {
			throw new UpmsException("用户名/密码错误");
		} catch (LockedAccountException e) {
			throw new UpmsException("该用户被锁定");
		} catch (DisabledAccountException e){
			throw new UpmsException("该用户被冻结");
		} catch (AuthenticationException e) {
			throw new UpmsException("用户不存在");
		} catch (Exception e) {
			throw new UpmsException("登录失败");
		}
		session.setTimeout(upmsProperties.getSessionTimeout());

		UpmsUser currentUser = ShiroAuthHelper.getCurrentUser();
		//返回Data
		UpmsUserModel userModel = EntityToModelUtil.entityToModel(currentUser, true);

		//获取登录用户的所有角色数据
		List<UpmsRoleModel> roleModelList = queryRole(currentUser.getId());
		userModel.setRoleIdSet(roleModelList.stream().map(UpmsRoleModel::getId).collect(Collectors.toSet()));
		userModel.setRoleCodeSet(roleModelList.stream().map(UpmsRoleModel::getCode).collect(Collectors.toSet()));
		//userModel.setRoleList(roleModelList);

		//获取登录用户的所有权限数据
		List<UpmsPermissionModel> permissionModelList = queryPermission(currentUser.getId(), null);
		userModel.setPermissionIdSet(permissionModelList.stream().map(UpmsPermissionModel::getId).collect(Collectors.toSet()));
		userModel.setPermissionCodeSet(permissionModelList.stream().map(UpmsPermissionModel::getCode).collect(Collectors.toSet()));
		//userModel.setPermissionList(permissionModelList);

		//获取登录用户的所有菜单（树形）
		List<UpmsPermissionModel> topTree = listToTree(permissionModelList, PermissionType.MENU);
		userModel.setPermissionTree(topTree);

		//登录返回token
		userModel.setToken(session.getId().toString());

		return userModel;
	}

	@Override
	public UpmsUserModel getSelfInfo(String token) {
		String sessionId = ShiroAuthHelper.getCurrentSessionId();
		if(!sessionId.equals(token)) {
			log.warn("Token不正确，不是当前用户，强制退出登录，返回超时信息");
			logout();
			ReturnCode errCode = ReturnCode.LOGIN_TIMEOUT;
			throw new UpmsException(errCode.getValue(), errCode.getName());
		}
		UpmsUser currentUser = ShiroAuthHelper.getCurrentUser();
		UpmsUser userEntity = upmsUserRepository.findByLoginName(currentUser.getLoginName());
		//返回Data
		UpmsUserModel userModel = EntityToModelUtil.entityToModel(userEntity, true);

		//获取登录用户的所有角色数据
		List<UpmsRoleModel> roleModelList = queryRole(userEntity.getId());
		userModel.setRoleIdSet(roleModelList.stream().map(UpmsRoleModel::getId).collect(Collectors.toSet()));
		userModel.setRoleCodeSet(roleModelList.stream().map(UpmsRoleModel::getCode).collect(Collectors.toSet()));
		//userModel.setRoleList(roleModelList);

		//获取登录用户的所有权限数据
		List<UpmsPermissionModel> permissionModelList = queryPermission(userEntity.getId(), null);
		userModel.setPermissionIdSet(permissionModelList.stream().map(UpmsPermissionModel::getId).collect(Collectors.toSet()));
		userModel.setPermissionCodeSet(permissionModelList.stream().map(UpmsPermissionModel::getCode).collect(Collectors.toSet()));
		//userModel.setPermissionList(permissionModelList);

		//获取登录用户的所有菜单（树形）
		List<UpmsPermissionModel> topTree = listToTree(permissionModelList, PermissionType.MENU);
		userModel.setPermissionTree(topTree);

		return userModel;
	}

	@Override
	public void logout() {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
	}

	@Override
	public List<UpmsUserModel> queryByRoleId(Long roleId) {
		List<UpmsUser> entityList = upmsUserRepository.findByRoleList_Id(roleId);
		return EntityToModelUtil.entityToModel4User(entityList);
	}

	@Override
	public List<UpmsUserModel> queryByIdSet(Set<Long> idSet) {
		List<UpmsUser> entityList = upmsUserRepository.findAllById(idSet);
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
		upmsUserRepository.freezeById(id);
	}

	@Override
	public void unfreezeById(Long id) {
		upmsUserRepository.unfreezeById(id);
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
