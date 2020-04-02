package me.zyb.framework.upms.dict;

import lombok.Getter;
import me.zyb.framework.core.base.BaseEnum;
import me.zyb.framework.core.convert.JpaEnumConverter;

/**
 * 权限类型
 * @author zhangyingbin
 */
@Getter
public enum LogType implements BaseEnum<Integer> {
	/** 登录 */
	LOGIN(0, "login", "登录"),
	/** 登出	 */
	LOGOUT(1, "logout", "登出"),
	/** 新增 */
	ADD(2, "add", "新增"),
	/** 编辑 */
	EDIT(3, "edit", "编辑"),
	/** 新增 */
	DELETE(4, "delete", "删除");

	private Integer value;
	private String code;
	private String name;

	LogType(Integer value, String code, String name){
		this.value = value;
		this.code = code;
		this.name = name;
	}

	/**
	 * 数据库实体转换器
	 */
	public static class Converter extends JpaEnumConverter<LogType, Integer> {}
}
