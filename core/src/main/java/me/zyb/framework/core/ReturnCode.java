package me.zyb.framework.core;

import lombok.Getter;
import me.zyb.framework.core.autoconfigure.MessageSourceHelper;
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
	/** 数据不存在 */
	DATA_NOT_EXIST("0002", "data.not.exist", "数据不存在"),
	/** 请求超时 */
	REQUEST_TIMEOUT("0003", "request.timeout", "请求超时"),
	/** 频繁请求 */
	REQUEST_FREQUENT("0004", "request.frequent", "频繁请求"),
	/** 验证码错误 */
	CAPTCHA_ERROR("0005", "captcha.error", "验证码错误"),
	/** 验证码超时 */
	CAPTCHA_TIMEOUT("0006", "captcha.timeout", "验证码超时"),
	/** 邮箱已注册 */
	REGISTERED_EMAIL("0007", "registered.email", "邮箱已注册"),
	/** 手机号已注册 */
	REGISTERED_MOBILE("0008", "registered.mobile", "手机号已注册"),
	/** 用户名已注册 */
	REGISTERED_USERNAME("0009", "registered.mobile", "用户名已注册"),
	/** 未登录/登录超时 */
	LOGIN_TIMEOUT("0010", "login.not", "未登录/登录超时"),
	/** 权限不足 */
	PERMISSION_DENIED("0011", "permission.denied", "权限不足"),
	/** 用户名/密码错误 */
	USERNAME_PASSWORD_ERROR("0012", "username.password.error", "用户名/密码错误"),
	/** 用户已冻结 */
	USER_FREEZE("0013", "user.freeze", "用户已冻结"),
	/** 非法操作 */
	ILLEGAL_OPERATION("0014", "illegal.operation", "非法操作"),

	/** 功能未上线 */
	FUNCTION_NOT_ONLINE("9995", "function.not.online", "功能未上线"),
	/** 功能已下线 */
	FUNCTION_OFFLINE("9996", "function.offline", "功能已下线"),
	/** 系统维护中 */
	SYSTEM_MAINTAIN("9997", "system.maintain", "系统维护中"),
	/** 系统繁忙 */
	SYSTEM_BUSY("9998", "system.busy", "系统繁忙"),
	/** 失败 */
	FAILURE("9999", "failure", "失败"),

	;

	/** 返回码 {@link ReturnData code}*/
	private String value;
	/** 国际化资源中的code（key） */
	private String code;
	/** 国际化资源中的value（默认值） */
	private String name;
	
	ReturnCode(String value, String code, String name) {
		this.value = value;
		this.code = code;
		this.name = name;
	}

	@Override
	public String getName(){
		return MessageSourceHelper.getMessage(code, null, name, null);
	}
}
