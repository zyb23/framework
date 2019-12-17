package me.zyb.framework.upms.configure;

import lombok.Data;
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

	/** 是否要确认密码（例如注册时，需要输入两次密码） */
	private Boolean switchConfirmPassword = true;
	/** 密码策略是否开启 */
	private Boolean switchValidatePasswordPolicy = true;
	/** 密码策略-最短 */
	private int passwordPolicyShortest = 8;
	/** 密码策略-最长 */
	private int passwordPolicyLongest = 32;

	/** 是否启用Shiro（有BUG） */
	//private Boolean switchShiro = true;
	/** 是否启用Shiro-Redis */
	private Boolean switchShiroRedis = true;

	/** 是否需要图片验证码（例如登录时） */
	private Boolean switchValidateImageCaptcha = true;
}
