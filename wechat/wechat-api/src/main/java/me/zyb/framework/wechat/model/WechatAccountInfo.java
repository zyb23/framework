package me.zyb.framework.wechat.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import me.zyb.framework.core.convert.JpaJsonConverter;

import java.io.Serializable;

/**
 * 微信第三方信息
 * @author zhangyingbin
 */
@Data
public class WechatAccountInfo implements Serializable{
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

	/**
	 * 数据实体转换器
	 */
	public static class Converter extends JpaJsonConverter<WechatAccountInfo> {}
}
