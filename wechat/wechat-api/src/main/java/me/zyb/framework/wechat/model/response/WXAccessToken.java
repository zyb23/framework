package me.zyb.framework.wechat.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 微信公众号获取access_token返回值
 * @author zhangyingbin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WXAccessToken extends WXBaseResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/** access_token */
	@JSONField(name = "access_token")
	private String accessToken;
	
	/** 有效时间，单位：秒 */
	@JSONField(name = "expires_in")
	private Integer expiresIn;
}
