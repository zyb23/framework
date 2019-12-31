package me.zyb.framework.core.base;

import me.zyb.framework.core.ReturnCode;
import me.zyb.framework.core.ReturnData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * @author zhangyingbin
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class BaseController {
	@Autowired
	protected HttpServletRequest request;

	@Autowired
	protected HttpServletResponse response;
	
	@Autowired
	protected MessageSource messageSource;
	
	/**
	 * 返回成功，数据对象为null
	 * @return ReturnData
	 */
	public ReturnData rtSuccess() {
		return rt(ReturnCode.SUCCESS);
	}
	
	/**
	 * 返回数据
	 * @param data	数据对象
	 * @return ReturnData
	 */
	public ReturnData rtSuccess(Object data) {
		return rt(ReturnCode.SUCCESS, data);
	}
	
	/**
	 * 返回失败，数据对象为null
	 * @return ReturnData
	 */
	public ReturnData rtFailure() {
		return rt(ReturnCode.FAILURE);
	}
	
	/**
	 * 返回失败，数据对象为null
	 * @param message	失败信息
	 * @return ReturnData
	 */
	public ReturnData rtFailure(String message) {
		return rt(ReturnCode.FAILURE.getValue(), message);
	}
	
	/**
	 * 返回失败
	 * @param data	数据对象
	 * @return ReturnData
	 */
	public ReturnData rtFailure(Object data) {
		return rt(ReturnCode.FAILURE, data);
	}

	/**
	 * 返回失败，参数错误
	 * @return ReturnData
	 */
	public ReturnData rtParameterError(){
		return rt(ReturnCode.PARAMETER_ERROR);
	}

	/**
	 * 返回失败，参数错误
	 * @param message 失败信息
	 * @return ReturnData
	 */
	public ReturnData rtParameterError(String message){
		return rt(ReturnCode.PARAMETER_ERROR, message);
	}
	
	/**
	 * 返回数据，数据对象为null
	 * @param returnCode    返回码
	 * @return ReturnData
	 */
	public ReturnData rt(ReturnCode returnCode) {
		return rt(returnCode, null, null, null);
	}
	
	/**
	 * 返回数据
	 * @param returnCode	返回码
	 * @param data			数据对象
	 * @return ReturnData
	 */
	public ReturnData rt(ReturnCode returnCode, Object data){
		return rt(returnCode, null, null, data);
	}
	
	/**
	 * 返回数据，数据对象为null
	 * @param returnCode	返回码
	 * @param args			返回信息占位符参数
	 * @return ReturnData
	 */
	public ReturnData rt(ReturnCode returnCode, Object[] args) {
		return rt(returnCode, args, null, null);
	}
	
	/**
	 * 返回数据
	 * @param returnCode	返回码
	 * @param args			返回信息占位符参数
	 * @param data			数据对象
	 * @return ReturnData
	 */
	public ReturnData rt(ReturnCode returnCode, Object[] args, Object data){
		return rt(returnCode, args, null, data);
	}
	
	/**
	 * 返回数据，数据对象为null
	 * @param returnCode		返回码
	 * @param defaultMessage	返回信息默认值
	 * @return ReturnData
	 */
	public ReturnData rt(ReturnCode returnCode, String defaultMessage) {
		return rt(returnCode, null, defaultMessage, null);
	}
	
	/**
	 * 返回数据
	 * @param returnCode		返回码
	 * @param defaultMessage	返回信息默认值
	 * @param data				数据对象
	 * @return ReturnData
	 */
	public ReturnData rt(ReturnCode returnCode, String defaultMessage, Object data) {
		return rt(returnCode, null, defaultMessage, data);
	}
	
	/**
	 * 返回数据
	 * @param returnCode		返回码
	 * @param args				返回信息占位符参数
	 * @param defaultMessage	返回信息默认值
	 * @param data				数据对象
	 * @return ReturnData
	 */
	public  ReturnData rt(ReturnCode returnCode, Object[] args, String defaultMessage, Object data) {
		Locale locale = request.getLocale();
		String message = messageSource.getMessage(returnCode.getCode(), args, defaultMessage, locale);
		message = (null == message) ? returnCode.getName() : message;

		return rt(returnCode.getValue(), message, data);
	}
	
	/**
	 * 返回数据：自定义返回码、返回信息，数据对象为null
	 * @param code		返回码
	 * @param message	返回信息
	 * @return ReturnData
	 */
	public static ReturnData rt(String code, String message) {
		return rt(code, message, null);
	}
	
	/**
	 * 返回数据：自定义返回码、返回信息
	 * @param code		返回码
	 * @param message	返回信息
	 * @param data		数据对象
	 * @return ReturnData
	 */
	public static ReturnData rt(String code, String message, Object data) {
		return new ReturnData(code, message, data);
	}
}
