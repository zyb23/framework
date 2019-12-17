package me.zyb.framework.core.base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * 基础异常类
 * @author zhangyingbin
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Slf4j
public class BaseException extends RuntimeException {

	/**
	 * 异常返回编码
	 */
	protected String code = "-1";

	public BaseException(){
		super();
		log.error(this.getMessage());
	}

	public BaseException(String message){
		super(message);
		log.error(message);
	}

	public BaseException(Throwable throwable){
		super(throwable);
		log.error(throwable.getMessage());
	}

	public BaseException(String message, Throwable throwable){
		super(message, throwable);
		log.error(message, throwable);
	}
}
