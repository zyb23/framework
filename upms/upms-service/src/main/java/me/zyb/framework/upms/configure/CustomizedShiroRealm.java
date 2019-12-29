package me.zyb.framework.upms.configure;

import lombok.extern.slf4j.Slf4j;
import me.zyb.framework.upms.entity.UpmsUser;
import me.zyb.framework.upms.model.UpmsPermissionModel;
import me.zyb.framework.upms.repository.UpmsUserRepository;
import me.zyb.framework.upms.service.UpmsUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Shiro自定义认证、授权领域
 * @author zhangyingbin
 */
@Slf4j
public class CustomizedShiroRealm extends AuthorizingRealm {
	@Autowired
	private UpmsUserRepository upmsUserRepository;
	@Autowired
	private UpmsUserService upmsUserService;

	public CustomizedShiroRealm(){
		CustomizedShiroCredentialsMatcher sccm = new CustomizedShiroCredentialsMatcher();
		this.setCredentialsMatcher(sccm);
	}

	/**
	 * 认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		log.debug("----------------------- Shiro 认证 -----------------------");
		//账户锁定
		//throw new LockedAccountException(); TODO
		//获取用户的输入的账号
		UsernamePasswordToken upToken = (UsernamePasswordToken)token;
		String loginName = upToken.getUsername();
		UpmsUser userEntity = upmsUserRepository.findByLoginName(loginName);
		if (userEntity == null) {
			throw new UnknownAccountException();
		}
		//账户冻结
		if (!userEntity.getIsEnable()) {
			throw new DisabledAccountException();
		}
		SimpleAuthenticationInfo saInfo = new SimpleAuthenticationInfo(userEntity, userEntity.getLoginPassword(), getName());
		log.debug("----------------------- Shiro 认证成功 -----------------------");
		return saInfo;
	}

	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		log.debug("----------------------- Shiro 授权 -----------------------");
		SimpleAuthorizationInfo saInfo = new SimpleAuthorizationInfo();
		UpmsUser userEntity = (UpmsUser) principals.getPrimaryPrincipal();
		List<UpmsPermissionModel> permissionModelList = upmsUserService.queryPermission(userEntity.getId(), null);
		Set<String> permissionCodeSet = permissionModelList.stream().map(UpmsPermissionModel::getCode).collect(Collectors.toSet());
		saInfo.addStringPermissions(permissionCodeSet);
		log.debug("----------------------- Shiro 授权成功 -----------------------");
		return saInfo;
	}

	/**
	 * 清空指定用户授权缓存
	 */
	@Override
	public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 清空所有用户授权的缓存
	 */
	public void clearAllCachedAuthorizationInfo() {
		getAuthorizationCache().clear();
	}
}
