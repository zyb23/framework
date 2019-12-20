package me.zyb.framework.wechat.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * 微信公众号access_token
 * @author zhangyingbin
 */
@Data
public class WechatAccessToken implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/** access_token */
	@JSONField(name = "access_token")
	private String accessToken;
	
	/** 有效时间，单位：秒 */
	@JSONField(name = "expires_in")
	private Integer expiresIn;
	
	/** 错误码 */
	private String errcode;
	
	/** 错误信息 */
	private String errmsg;
	
}
