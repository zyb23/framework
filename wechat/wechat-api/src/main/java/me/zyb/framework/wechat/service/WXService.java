package me.zyb.framework.wechat.service;


import me.zyb.framework.wechat.model.response.WXAccessToken;
import me.zyb.framework.wechat.model.response.WXCode2Session;

/**
 * 微信基础服务
 * @author zhangyingbin
 */
public interface WXService {
	/**
	 * <p>服务器配置校验（微信签名校验）<p/>
	 * <p>调用此方法，配置文件中（yml、properties）wechat.appKey必须配置</p>
	 * @author zhangyingbin
	 * @param signature 加密签名（微信传的参数）
	 * @param timestamp 时间戳（微信传的参数）
	 * @param nonce     随机数（微信传的参数）
	 * @return boolean
	 */
	public boolean checkSignature(String signature, String timestamp, String nonce);

	/**
	 * <p>从微信获取access_token，并刷新数据库中WechatConfig的access_token</p>
	 * <p>调用此方法，配置文件中（yml、properties）wechat.appKey必须配置</p>
	 * @author zhangyingbin
	 * @return WXAccessToken
	 */
	public WXAccessToken refreshAccessToken();

	/**
	 * 登录凭证校验
	 * @param code  登录时获取的code
	 * @return WXCode2Session
	 */
	public WXCode2Session authCode2Session(String code);
}
