package me.zyb.framework.upms;

/**
 * @author zhangyingbin
 */
public class UpmsException extends RuntimeException {
	public UpmsException(){
		super();
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
