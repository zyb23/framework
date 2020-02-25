package me.zyb.framework.usoa.dict;

import lombok.Getter;
import me.zyb.framework.core.base.BaseEnum;

/**
 * 登录方式
 * @author zhangyingbin
 */
@Getter
public enum LoginMode implements BaseEnum<Integer> {
	/** 短信验证码 */
	SMS_CODE(1, "smsCode", "短信验证码"),
	/** 登录密码	 */
	LOGIN_PASSWORD(2, "loginPassword", "登录密码"),
	/** 微信 */
	WECHAT(3, "wechat", "微信");

	private Integer value;
	private String code;
	private String name;

	LoginMode(Integer value, String code, String name){
		this.value = value;
		this.code = code;
		this.name = name;
	}
}
