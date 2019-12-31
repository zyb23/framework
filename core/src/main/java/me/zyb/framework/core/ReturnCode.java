package me.zyb.framework.core;

import lombok.Getter;
import me.zyb.framework.core.base.BaseEnum;

/**
 * 返回码
 * @author Administrator
 *
 */
@Getter
public enum ReturnCode implements BaseEnum<String> {
	/** 成功 */
	SUCCESS("0000", "success", "成功"),
	/** 参数错误 */
	PARAMETER_ERROR("0001", "parameter.error", "参数错误"),
	/** 请求超时 */
	REQUEST_TIMEOUT("0002", "request.timeout", "请求超时"),

	/** 验证码错误 */
	CAPTCHA_ERROR("1000", "captcha.error", "验证码错误"),
	/** 权限不足 */
	PERMISSION_DENIED("1001", "permission.denied", "权限不足"),
	/** 未登录/登录超时 */
	LOGIN_TIMEOUT("1002", "login.not", "未登录/登录超时"),
	/** 用户名/密码错误 */
	USERNAME_PASSWORD_ERROR("1003", "username.password.error", "用户名/密码错误"),

	/** 功能未上线 */
	FUNCTION_NOT_ONLINE("8997", "function.not.online", "功能未上线"),
	/** 功能已下线 */
	FUNCTION_OFFLINE("8998", "function.offline", "功能已下线"),
	/** 系统维护中 */
	SYSTEM_MAINTAIN("8999", "system.maintain", "系统维护中"),

	/** 系统繁忙 */
	SYSTEM_BUSY("9998", "system.busy", "系统繁忙"),
	/** 失败 */
	FAILURE("9999", "failure", "失败"),

	;
	
	private String value;
	private String code;
	private String name;
	
	ReturnCode(String value, String code, String name) {
		this.value = value;
		this.code = code;
		this.name = name;
	}

//	public static ReturnCode getByValue(String value){
//        for(ReturnCode en : ReturnCode.values()){
//            if(en.getValue().equals(value)){
//                return en;
//            }
//        }
//        return  null;
//    }
//
//	public static ReturnCode getByCode(String code) {
//		for(ReturnCode en : ReturnCode.values()) {
//			if(en.getCode().equals(code)) {
//				return en;
//			}
//		}
//		return null;
//	}
//
//	public static ReturnCode getByName(String name) {
//		for(ReturnCode en : ReturnCode.values()) {
//			if(en.getName().equals(name)) {
//				return en;
//			}
//		}
//		return null;
//	}
}
