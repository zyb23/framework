package me.zyb.framework.core.constant;

/**
 * 数值常量
 * @author zhangyingbin
 */
public class ConstNumber {
	/** 验证码请求频率（秒） */
	public static final Integer CAPTCHA_FREQUENT = 60;
	/** 验证码单日请求上限 */
	public static final Integer CAPTCHA_LIMIT_DAY = 10;
	/** 验证码单月请求上限 */
	public static final Integer CAPTCHA_LIMIT_MONTH = 100;
	/** 验证码字符个数 */
	public static final Integer CAPTCHA_SIZE = 6;
	/** 验证码超时时间（秒） */
	public static final Integer CAPTCHA_TIMEOUT = 300;

	/** 默认集合初始化容量大小 */
	public static final Integer INITIAL_CAPACITY = 16;

	/** 默认分页页码 */
	public static final Integer PAGE_NO = 1;
	/** 默认分页条数 */
	public static final Integer PAGE_SIZE = 10;

	/** 默认Session超时时间（1小时-3600_000毫秒） */
	public static final Long SESSION_TIMEOUT = 3600_000L;
}
