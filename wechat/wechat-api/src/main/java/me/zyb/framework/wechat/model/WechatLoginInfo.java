package me.zyb.framework.wechat.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import me.zyb.framework.core.convert.JpaJsonConverter;

import java.io.Serializable;

/**
 * 微信第三方登录信息
 * @author zhangyingbin
 */
@Data
public class WechatLoginInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/** openid */
	@JSONField(name = "openid")
	private String openId;
	
	/** unionid */
	@JSONField(name = "unionid")
	private String unionid;

	/** 昵称 */
	private String nickname;

	/** 头像 */
	private String icon;

	/** 会话密钥 */
	@JSONField(name = "session_key")
	private String sessionKey;
	
	/** 错误码 */
	@JSONField(name = "errcode")
	private String errCode;
	
	/** 错误信息 */
	@JSONField(name = "errmsg")
	private String errMsg;

	/**
	 * 数据实体转换器
	 */
	public static class Converter extends JpaJsonConverter<WechatLoginInfo> {}

	/** 请求微信信息成功时返回码 */
	public static Integer ERR_CODE_OK = 0;

	public boolean isSuccess(){
		return this.errCode == null || ERR_CODE_OK.equals(errCode);
	}
}
