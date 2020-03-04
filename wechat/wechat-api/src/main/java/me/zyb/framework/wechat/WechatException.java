package me.zyb.framework.wechat;

import me.zyb.framework.core.base.BaseException;

/**
 * @author zhangyingbin
 */
public class WechatException extends BaseException {
	public WechatException(){
		super();
	}

	public WechatException(String code, String message){
		super(code, message);
	}

	public WechatException(String message){
		super(message);
	}

	public WechatException(Throwable throwable){
		super(throwable);
	}

	public WechatException(String message, Throwable throwable){
		super(message, throwable);
	}
}
