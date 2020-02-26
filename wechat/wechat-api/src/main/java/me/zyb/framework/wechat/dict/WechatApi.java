package me.zyb.framework.wechat.dict;

/**
 * 微信开发接口请求地址
 * @author zhangyingbin
 */
public class WechatApi {
	/** 登录凭证校验 */
	public static final String AUTH_CODE_2_SESSION = "https://api.weixin.qq.com/sns/jscode2session?grant_type={0}&appid={1}&secret={2}&js_code={3}";

	/** 获取access_token */
	public static final String GET_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type={0}&appid={1}&secret={2}";
	/** 获取微信API接口 IP地址（api.weixin.qq.com的解析地址，由开发者调用微信侧的接入IP） */
	public static final String GET_IP_API_DOMAIN = "https://api.weixin.qq.com/cgi-bin/get_api_domain_ip?access_token={0}";
	/** 获取微信callback IP地址（微信调用开发者服务器所使用的出口IP） */
	public static final String GET_IP_CALLBACK = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token={0}";

	/** 创建菜单 */
	public static final String MENU_CREATE = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token={0}";
}
