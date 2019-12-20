package me.zyb.framework.wechat.configure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhangyingbin
 */
@Data
@Component
@ConfigurationProperties(prefix = WechatProperties.UPMS_PREFIX)
public class WechatProperties {
	public static final String UPMS_PREFIX = "wechat";

	/** 应用标识 */
	private String appKey;
}
