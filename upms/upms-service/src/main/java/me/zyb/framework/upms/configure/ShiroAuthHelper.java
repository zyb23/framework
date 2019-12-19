package me.zyb.framework.upms.configure;

import me.zyb.framework.upms.entity.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @author zhangyingbin
 */
public final class ShiroAuthHelper {

	/** Session中检验验证码的Key */
	public static final String SESSION_KEY_CAPTCHA_KEY = "SESSION_KEY_CAPTCHA_KEY";
	/** Session中的验证码 */
	public static final String SESSION_KEY_CAPTCHA = "SESSION_KEY_CAPTCHA";

    private ShiroAuthHelper() {
    }

    /**
     * <p>清空Shiro(当前登录)用户证认/授权缓存</p>
     * <p>目前只缓存授权，认证因只登录一次且未实现会话持久话（分布式会话）故没有缓存认证</p>
     * <p>可用于用户登录前，或注销后 清空授权缓存信息</p>
     */
    public static void clearCurrentUserCachedAuthorizationInfo() {
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        RealmSecurityManager securityManager = (RealmSecurityManager) SecurityUtils.getSecurityManager();
        securityManager.getRealms().forEach(realm -> {
            if (realm instanceof CustomizedShiroRealm) {
                ((CustomizedShiroRealm) realm).clearCachedAuthorizationInfo(principals);
            }
        });
    }

    /**
     * <p>清空Shiro(所有)用户证认/授权缓存</p>
     * <p>目前只缓存授权，认证因只登录一次且未实现会话持久话（分布式会话）故没有缓存认证</p>
     * <p>用于修改角色权限时调用，清除所有用户权限</p>
     */
    public static void clearAllCachedAuthorizationInfo() {
        RealmSecurityManager securityManager = (RealmSecurityManager) SecurityUtils.getSecurityManager();
        securityManager.getRealms().forEach(realm -> {
            if (realm instanceof CustomizedShiroRealm) {
                ((CustomizedShiroRealm) realm).clearAllCachedAuthorizationInfo();
            }
        });
    }

    /**
     * 获取当前登录用户
     */
    public static SysUser getCurrentSysUser() {
        return (SysUser) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 获取当前登录用户ID
     */
    public static Long getCurrentSysUserId() {
	    SysUser userEntity = getCurrentSysUser();
        if (null != userEntity) {
            return userEntity.getId();
        }
        return null;
    }

	/**
	 * 获取当前会话
	 */
	public static Session getCurrentSession() {
        return SecurityUtils.getSubject().getSession(true);
    }

}
