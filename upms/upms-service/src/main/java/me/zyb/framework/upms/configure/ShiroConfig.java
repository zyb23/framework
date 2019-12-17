package me.zyb.framework.upms.configure;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author zhangyingbin
 */
@Configuration
public class ShiroConfig {
	@Value("${spring.redis.host: 127.0.0.1}")
	private String host;
	@Value("${spring.redis.port: 6379}")
	private int port;
	@Value("${spring.redis.password: }")
	private String password;
	@Value("${spring.redis.database: 0}")
	private int database;

	@ConditionalOnProperty(prefix = "upms", value = "switch-shiro", havingValue = "true", matchIfMissing = true)
	@Bean
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		//配置shiro默认登录界面地址（未登录会重定向到这），前后端分离中登录界面跳转应由前端路由控制，后台仅返回json数据
		shiroFilterFactoryBean.setLoginUrl("/auth/index");
		//登录成功后要跳转的链接
		shiroFilterFactoryBean.setSuccessUrl("/auth/home");
		//未授权界面（然并卵）
		shiroFilterFactoryBean.setUnauthorizedUrl("/auth/403");

		//权限控制链（有序）
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		filterChainDefinitionMap.put("/auth/login", "anon");
		filterChainDefinitionMap.put("/auth/logout", "logout");
		filterChainDefinitionMap.put("/captcha/**", "anon");
		filterChainDefinitionMap.put("/static/**", "anon");
		filterChainDefinitionMap.put("/**", "authc");

		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}

	@Bean
	public CustomizedShiroRealm customizedShiroRealm(){
		return new CustomizedShiroRealm();
	}

	@ConditionalOnProperty(prefix = "upms", value = "switch-shiro-redis", havingValue = "false")
	@Bean
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(customizedShiroRealm());
		securityManager.setSessionManager(sessionManager());
		return securityManager;
	}

	/**
	 * shiroRedisEnable默认为true（未配置时，此条成立）
	 */
	@ConditionalOnProperty(prefix = "upms", value = "switch-shiro-redis", havingValue = "true", matchIfMissing = true)
	@Bean
	public SecurityManager redisSecurityManager(SessionManager sessionManager, RedisCacheManager redisCacheManager) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(customizedShiroRealm());
		securityManager.setSessionManager(sessionManager);
		securityManager.setCacheManager(redisCacheManager);
		return securityManager;
	}

	/**
	 * 自己定义Session管理
	 */
	@ConditionalOnProperty(prefix = "upms", value = "switch-shiro-redis", havingValue = "false")
	@Bean
	public SessionManager sessionManager() {
		return new CustomizedShiroSessionManager();
	}

	/**
	 * <p>shiroRedisEnable默认为true（未配置时，此条成立）</p>
	 * <p>自己定义Session管理，使用Redis</p>
	 */
	@ConditionalOnProperty(prefix = "upms", value = "switch-shiro-redis", havingValue = "true", matchIfMissing = true)
	@Bean
	public SessionManager redisSessionManager(RedisSessionDAO redisSessionDAO) {
		CustomizedShiroSessionManager sessionManager = new CustomizedShiroSessionManager();
		sessionManager.setSessionDAO(redisSessionDAO);
		return sessionManager;
	}

	/**
	 * cacheManager 缓存 redis实现，使用的是shiro-redis开源插件
	 */
	@ConditionalOnProperty(prefix = "upms", value = "switch-shiro-redis", havingValue = "true", matchIfMissing = true)
	@Bean
	public RedisCacheManager redisCacheManager(RedisManager redisManager) {
		RedisCacheManager redisCacheManager = new RedisCacheManager();
		redisCacheManager.setRedisManager(redisManager);
		return redisCacheManager;
	}

	/**
	 * RedisSessionDAO shiro sessionDao层的实现 通过redis，使用的是shiro-redis开源插件
	 */
	@ConditionalOnProperty(prefix = "upms", value = "switch-shiro-redis", havingValue = "true", matchIfMissing = true)
	@Bean
	public RedisSessionDAO redisSessionDAO(RedisManager redisManager) {
		RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
		redisSessionDAO.setRedisManager(redisManager);
		return redisSessionDAO;
	}

	/**
	 * 配置shiro redisManager，使用的是shiro-redis开源插件
	 */
	@ConditionalOnProperty(prefix = "upms", value = "switch-shiro-redis", havingValue = "true", matchIfMissing = true)
	@Bean
	public RedisManager redisManager() {
		RedisManager redisManager = new RedisManager();
		redisManager.setHost(host.trim() + ":" + port);
		if(!StringUtils.isBlank(password.trim())){
			redisManager.setPassword(password);
		}
		redisManager.setDatabase(database);
		return redisManager;
	}


	/**
	 * 开启shiro aop注解支持
	 * 使用代理方式：所以需要开启代码支持
	 */
	@ConditionalOnProperty(prefix = "upms", value = "switch-shiro-redis", havingValue = "false")
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

	/**
	 * 开启shiro aop注解支持
	 * 使用代理方式：所以需要开启代码支持
	 */
	@ConditionalOnProperty(prefix = "upms", value = "switch-shiro-redis", havingValue = "true", matchIfMissing = true)
	@Bean
	public AuthorizationAttributeSourceAdvisor redisAuthorizationAttributeSourceAdvisor(SecurityManager redisSecurityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(redisSecurityManager);
		return authorizationAttributeSourceAdvisor;
	}
}
