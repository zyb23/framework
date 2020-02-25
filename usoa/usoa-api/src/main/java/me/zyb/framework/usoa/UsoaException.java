package me.zyb.framework.usoa;

/**
 * @author zhangyingbin
 */
public class UsoaException extends RuntimeException {
	public UsoaException(){
		super();
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
