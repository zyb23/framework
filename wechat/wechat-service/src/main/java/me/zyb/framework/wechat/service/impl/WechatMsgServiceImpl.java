package me.zyb.framework.wechat.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.zyb.framework.wechat.WechatException;
import me.zyb.framework.wechat.dict.EventType;
import me.zyb.framework.wechat.dict.MsgType;
import me.zyb.framework.wechat.model.WechatMsg;
import me.zyb.framework.wechat.service.WechatMsgService;
import org.springframework.stereotype.Service;

/**
 * @author zhangyingbin
 */
@Slf4j
@Service
public class WechatMsgServiceImpl implements WechatMsgService {
	@Override
	public WechatMsg handleRequest(WechatMsg msgRequest) {
		String msgType = msgRequest.getMsgType();
		WechatMsg msgResponse = new WechatMsg();
		switch (msgType){
			case MsgType.EVENT:{
				msgResponse = handleMsgEvent(msgRequest);
				break;
			}
			case MsgType.IMAGE:{
				break;
			}
			case MsgType.LINK:{
				break;
			}
			case MsgType.LOCATION:{
				break;
			}
			case MsgType.SHORTVIDEO:{
				break;
			}
			case MsgType.TEXT:{
				msgResponse = handleMsgText(msgRequest);
				break;
			}
			case MsgType.VIDEO:{
				break;
			}
			case MsgType.VOICE:{
				break;
			}
			default:{
				log.error("MsgType错误：", msgType);
				throw new WechatException("MsgType错误");
			}
		}
		return msgResponse;
	}

	@Override
	public WechatMsg handleMsgEvent(WechatMsg msgRequest) {
		String event = msgRequest.getEvent();
		WechatMsg msgResponse = buildMsgResponse(msgRequest);
		switch (event){
			case EventType.CLICK:{
				break;
			}
			case EventType.LOCATION:{
				break;
			}
			case EventType.SCAN:{
				break;
			}
			case EventType.SUBSCRIBE:{
				msgResponse.setMsgType(MsgType.TEXT);
				msgResponse.setContent("亲爱的，终于等到你来啦！");
				break;
			}
			case EventType.UNSUBSCRIBE:{
				break;
			}
			case EventType.VIEW:{
				break;
			}
			default:{
				log.error("Event错误：", event);
				throw new WechatException("Event错误");
			}
		}
		return msgResponse;
	}

	/**
	 * 构造响应消息基本参数
	 * @param msgRequest    响应消息
	 * @return WechatMsg
	 */
	private WechatMsg buildMsgResponse(WechatMsg msgRequest){
		WechatMsg msgResponse = new WechatMsg();
		msgResponse.setFromUserName(msgRequest.getToUserName());
		msgResponse.setToUserName(msgRequest.getFromUserName());
		msgResponse.setCreateTime(System.currentTimeMillis());
		return msgResponse;
	}

	@Override
	public WechatMsg handleMsgText(WechatMsg msgRequest) {
		WechatMsg msgResponse = buildMsgResponse(msgRequest);
		String content = msgRequest.getContent();
		msgResponse.setMsgType(MsgType.TEXT);
		if("我爱你".equals(content)){
			msgResponse.setContent("I \uD83D\uDC96 You Too!!!");
		}
		return msgResponse;
	}
}
