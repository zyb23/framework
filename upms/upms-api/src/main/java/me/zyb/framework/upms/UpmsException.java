package me.zyb.framework.upms;

import me.zyb.framework.core.base.BaseException;

/**
 * @author zhangyingbin
 */
public class UpmsException extends BaseException {
	public UpmsException(){
		super();
	}

	public UpmsException(String code, String message){
		super(code, message);
	}

	public UpmsException(String message){
		super(message);
	}

	public UpmsException(Throwable throwable){
		super(throwable);
	}

	public UpmsException(String message, Throwable throwable){
		super(message, throwable);
	}
}
