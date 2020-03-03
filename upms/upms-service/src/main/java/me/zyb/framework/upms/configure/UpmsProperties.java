package me.zyb.framework.upms.configure;

import lombok.Data;
import me.zyb.framework.core.dict.ConstNumber;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhangyingbin
 */
@Data
@Component
@ConfigurationProperties(prefix = UpmsProperties.UPMS_PREFIX)
public class UpmsProperties {
	public static final String UPMS_PREFIX = "upms";

	/** 密码策略-最长 */
	private int passwordPolicyLongest = 32;
	/** 密码策略-最短 */
	private int passwordPolicyShortest = 8;

	/** session超时 */
	private long sessionTimeout = ConstNumber.DEFAULT_SESSION_TIMEOUT;

	/** shiro不拦截路径，多个用逗号隔开 */
	private String shiroAnon;

	/** 是否要两次确认密码（例如注册时，需要输入两次密码） */
	private Boolean switchConfirmPassword = true;
	/** 是否需要图片验证码（例如登录时） */
	private Boolean switchImageCaptcha = true;
	/** 开启shiro aop注解支持（AuthorizationAttributeSourceAdvisor） */
	private Boolean switchShiroAop = true;
	/** 是否开启Shiro拦截器 */
	private Boolean switchShiroAuthc = true;
	/** 是否开启Shiro-Redis */
	private Boolean switchShiroRedis = true;
	/** 密码策略是否开启 */
	private Boolean switchPasswordPolicy = true;
}
