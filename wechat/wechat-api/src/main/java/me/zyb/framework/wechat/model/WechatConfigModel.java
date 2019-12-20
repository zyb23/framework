package me.zyb.framework.wechat.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 权限模型
 * @author zhangyingbin
 */
@Data
public class WechatConfigModel implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 主键ID */
	private Long id;

	/** 开发者ID是公众号开发识别码，配合开发者密码可调用公众号的接口能力。 */
	@NotBlank(message = "开发者ID不能为空")
	private String appId;

	/** 开发者密码是校验公众号开发者身份的密码，具有极高的安全性。切记勿把密码直接交给第三方开发者或直接存储在代码中。如需第三方代开发公众号，请使用授权方式接入。 */
	@NotBlank(message = "开发者密码不能为空")
	private String appSecret;

	/** access_token */
	private String accessToken;

	/** 服务器校验signature的URL：必须以http://或https://开头，分别支持80端口和443端口。 */
	@NotBlank(message = "服务器地址（URL）不能为空")
	private String url;

	/** 令牌：必须为英文或数字，长度为3-32字符。 */
	@NotBlank(message = "Token不能为空")
	private String token;

	/** 消息加密密钥：由43位字符组成，可随机修改，字符范围为A-Z，a-z，0-9。 */
	@NotBlank(message = "消息加密密钥不能为空")
	private String encodingAesKey;

	/** 消息加密方式 */
	private String encryptMode;

	/** 应用标识 */
	@NotBlank(message = "应用标识不能为空")
	private String appKey;

	/** 状态（只有一个状态为true） */
	private Boolean isEnable;
}
