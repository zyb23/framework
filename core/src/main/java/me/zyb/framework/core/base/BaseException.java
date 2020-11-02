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
	protected String code = null;

	public BaseException(){
		super();
		log.error(this.getMessage());
	}

	public BaseException(BaseEnum<String> baseEnum, String... message){
		super((message != null && message.length > 0) ? message[0] : baseEnum.getName());
		this.code = baseEnum.getValue();
		log.error("code:{}, message:{}", this.code, (message != null && message.length > 0) ? message[0] : baseEnum.getName());
	}

	public BaseException(String code, String message){
		super(message);
		this.code = code;
		log.error("code:{}, message:{}", code, message);
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
