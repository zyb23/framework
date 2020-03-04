package me.zyb.framework.gecs;

import me.zyb.framework.core.base.BaseException;

/**
 * @author zhangyingbin
 */
public class GecsException extends BaseException {
	public GecsException(){
		super();
	}

	public GecsException(String code, String message){
		super(code, message);
	}

	public GecsException(String message){
		super(message);
	}

	public GecsException(Throwable throwable){
		super(throwable);
	}

	public GecsException(String message, Throwable throwable){
		super(message, throwable);
	}
}
