package me.zyb.framework.gecs;

/**
 * @author zhangyingbin
 */
public class GecsException extends RuntimeException {
	public GecsException(){
		super();
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
