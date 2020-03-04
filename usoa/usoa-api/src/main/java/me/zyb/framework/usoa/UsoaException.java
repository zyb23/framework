package me.zyb.framework.usoa;

import me.zyb.framework.core.base.BaseException;

/**
 * @author zhangyingbin
 */
public class UsoaException extends BaseException {
	public UsoaException(){
		super();
	}

	public UsoaException(String code, String message){
		super(code, message);
	}

	public UsoaException(String message){
		super(message);
	}

	public UsoaException(Throwable throwable){
		super(throwable);
	}

	public UsoaException(String message, Throwable throwable){
		super(message, throwable);
	}
}
