package me.zyb.framework.wechat.entity;

import lombok.Data;
import me.zyb.framework.wechat.dict.EncryptMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 微信公众号-开发-基本配置
 * @author zhangyingbin
 *
 */
@Data
@Entity
@Table(name = "t_wechat_config")
public class WechatConfig implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/** 主键ID */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	/** 开发者ID是公众号开发识别码，配合开发者密码可调用公众号的接口能力。 */
	@Column(name = "app_id", nullable = false)
	private String appId;
	
	/** 开发者密码是校验公众号开发者身份的密码，具有极高的安全性。切记勿把密码直接交给第三方开发者或直接存储在代码中。如需第三方代开发公众号，请使用授权方式接入。 */
	@Column(name = "app_secret", nullable = false)
	private String appSecret;
	
	/** access_token */
	@Column(name = "access_token")
	private String accessToken;
	
	/** 服务器校验signature的URL：必须以http://或https://开头，分别支持80端口和443端口。 */
	@Column(name = "url")
	private String url;

	/** 令牌：必须为英文或数字，长度为3-32字符。 */
	@Column(name = "token", nullable = false)
	private String token;
	
	/** 消息加密密钥：由43位字符组成，可随机修改，字符范围为A-Z，a-z，0-9。 */
	@Column(name = "encoding_aes_key", nullable = false)
	private String encodingAesKey;
	
	/** 消息加密方式 */
	@Column(name = "encrypt_mode", nullable = false)
	private String encryptMode = EncryptMode.PLAINTEXT;
	
	/** 应用标识 */
	@Column(name = "app_key", nullable = false)
	private String appKey;
	
	/** 状态（只有一个状态为true） */
	@Column(name = "is_enable")
	private Boolean isEnable = true;
	
	/** 创建时间 */
	@Column(name = "create_time", nullable = false, updatable = false)
	private Date createTime = new Date();
	
	/** 修改时间 */
	@Column(name = "edit_time", nullable = false, updatable = false)
	private Date editTime = new Date();
	
}