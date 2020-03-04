package me.zyb.framework.upms.configure;

import me.zyb.framework.upms.entity.UpmsUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @author zhangyingbin
 */
public final class ShiroAuthHelper {

	/** Session中的图片验证码 */
	public static final String SESSION_IMAGE_CAPTCHA = "SESSION_IMAGE_CAPTCHA";

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
     * <pre>
     *     获取当前登录用户
     *     注：获取到的用户没有关联数据（角色、权限）
     * </pre>
     * @return UpmsUser
     */
    public static UpmsUser getCurrentUser() {
        return (UpmsUser) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 获取当前登录用户ID
     * @return Long
     */
    public static Long getCurrentUserId() {
	    UpmsUser userEntity = getCurrentUser();
        if (null != userEntity) {
            return userEntity.getId();
        }
        return null;
    }

	/**
	 * <pre>
	 *     获取当前会话
	 *     在未登录的情况下（subject.login），shiro每次获取的session都不同
	 * </pre>
	 * @return Session
	 */
	public static Session getCurrentSession() {
        return SecurityUtils.getSubject().getSession(true);
    }

	/**
	 * 获取当前用户sessionId
	 * @return String
	 */
	public static String getCurrentSessionId() {
		return getCurrentSession().getId().toString();
    }

}
