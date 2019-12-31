package me.zyb.framework.wechat.dict;

import lombok.Getter;
import me.zyb.framework.core.base.BaseEnum;
import me.zyb.framework.core.convert.JpaEnumConverter;

/**
 * 微信菜单级别
 * @author zhangyingbin
 */
@Getter
public enum WechatMenuLevel implements BaseEnum<Integer> {
	/** 一级菜单 */
	FIRST(1, "first", "一级菜单"),
	/** 二级菜单	 */
	SECOND(2, "second", "二级菜单"),
	;

	/** 一级菜单最多3个 */
	public static final Integer FIRST_MAX = 3;
	/** 二级菜单每组最多5个 */
	public static final Integer SECOND_MAX = 5;

	private Integer value;
	private String code;
	private String name;

	WechatMenuLevel(Integer value, String code, String name){
		this.value = value;
		this.code = code;
		this.name = name;
	}

	/**
	 * 数据库实体转换器
	 */
	public static class Converter extends JpaEnumConverter<WechatMenuLevel, Integer> {}
}
