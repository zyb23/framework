package me.zyb.framework.wechat;

/**
 * @author zhangyingbin
 */
public class WechatException extends RuntimeException {
	public WechatException(){
		super();
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
