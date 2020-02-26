package me.zyb.framework.usoa.model;

import lombok.Data;
import me.zyb.framework.usoa.dict.LoginMode;
import me.zyb.framework.wechat.model.WechatLoginInfo;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author zhangyingbin
 */
@Data
public class UsoaAccountLoginModel implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 登录方式 */
	@NotBlank(message = "登录方式不能为空")
	private LoginMode loginMode;

	/** 手机号 */
	private String mobile;

	/** 验证码 */
	private String verificationCode;

	/** 登录密码 */
	private String loginPassword;

	/** 微信登录信息 */
	private WechatLoginInfo wechatLoginInfo;
}
