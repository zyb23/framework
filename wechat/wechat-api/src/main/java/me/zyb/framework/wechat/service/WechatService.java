package me.zyb.framework.wechat.service;


import me.zyb.framework.wechat.model.WechatAccessToken;

/**
 * @author zhangyingbin
 */
public interface WechatService {
	/**
	 * 服务器配置校验（微信签名校验）
	 * @author zhangyingbin
	 * @param appKey    应用标识
	 * @param signature 微信加密签名
	 * @param timestamp 时间戳
	 * @param nonce     随机数
	 * @return boolean
	 */
	public boolean checkSignature(String appKey, String signature, String timestamp, String nonce);

	/**
	 * 获取access_token
	 * @author zhangyingbin
	 * @param appId     开发者ID
	 * @param appSecret 开发者密码
	 * @return WechatAccessToken
	 */
	public WechatAccessToken getAccessToken(String appId, String appSecret);
}
