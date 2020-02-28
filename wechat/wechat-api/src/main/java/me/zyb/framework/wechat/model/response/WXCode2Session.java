package me.zyb.framework.wechat.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 登录凭证校验返回值（auth.code2Session）
 * @author zhangyingbin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WXCode2Session extends WXBaseResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/** openid */
	@JSONField(name = "openid")
	private String openId;

	/** 会话密钥 */
	@JSONField(name = "session_key")
	private String sessionKey;
	
	/** unionid */
	@JSONField(name = "unionid")
	private String unionid;

}
