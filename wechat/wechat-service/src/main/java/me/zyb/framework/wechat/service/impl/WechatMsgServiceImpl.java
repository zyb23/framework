package me.zyb.framework.wechat.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.zyb.framework.wechat.WechatException;
import me.zyb.framework.wechat.dict.WechatEventType;
import me.zyb.framework.wechat.dict.WechatMsgType;
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
			case WechatMsgType.EVENT:{
				msgResponse = handleMsgEvent(msgRequest);
				break;
			}
			case WechatMsgType.IMAGE:{
				break;
			}
			case WechatMsgType.LINK:{
				break;
			}
			case WechatMsgType.LOCATION:{
				break;
			}
			case WechatMsgType.SHORTVIDEO:{
				break;
			}
			case WechatMsgType.TEXT:{
				msgResponse = handleMsgText(msgRequest);
				break;
			}
			case WechatMsgType.VIDEO:{
				break;
			}
			case WechatMsgType.VOICE:{
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
			case WechatEventType.CLICK:{
				break;
			}
			case WechatEventType.LOCATION:{
				break;
			}
			case WechatEventType.SCAN:{
				break;
			}
			case WechatEventType.SUBSCRIBE:{
				msgResponse.setMsgType(WechatMsgType.TEXT);
				msgResponse.setContent("亲爱的，终于等到你来啦！");
				break;
			}
			case WechatEventType.UNSUBSCRIBE:{
				break;
			}
			case WechatEventType.VIEW:{
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
		msgResponse.setMsgType(WechatMsgType.TEXT);
		if("我爱你".equals(content)){
			msgResponse.setContent("I \uD83D\uDC96 You Too!!!");
		}
		return msgResponse;
	}
}
