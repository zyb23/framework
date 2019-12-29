package me.zyb.framework.wechat.service;

import me.zyb.framework.wechat.model.WechatMsg;

/**
 * 微信消息服务
 * @author zhangyingbin
 */
public interface WechatMsgService {
	/**
	 * 处理微信推送的消息
	 * @param msgRequest    消息
	 * @return WechatMsg
	 */
	public WechatMsg handleRequest(WechatMsg msgRequest);

	/**
	 * 处理事件消息
	 * @param msgRequest    事件消息
	 * @return WechatMsg
	 */
	public WechatMsg handleMsgEvent(WechatMsg msgRequest);

	/**
	 * 处理客户发送的文本消息
	 * @param msgRequest    文本消息
	 * @return WechatMsg
	 */
	public WechatMsg handleMsgText(WechatMsg msgRequest);
}
