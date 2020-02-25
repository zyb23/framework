package me.zyb.framework.usoa.dict;

import lombok.Getter;
import me.zyb.framework.core.base.BaseEnum;
import me.zyb.framework.core.convert.JpaEnumConverter;

/**
 * 账号状态
 * @author zhangyingbin
 */
@Getter
public enum AccountState implements BaseEnum<Integer> {
	/** 正常 */
	NORMAL(1, "normal", "正常"),
	/** 锁定	 */
	LOCK(2, "lock", "锁定"),
	/** 按钮 */
	FREEZE(3, "freeze", "冻结");

	private Integer value;
	private String code;
	private String name;

	AccountState(Integer value, String code, String name){
		this.value = value;
		this.code = code;
		this.name = name;
	}

	/**
	 * 数据库实体转换器
	 */
	public static class Converter extends JpaEnumConverter<AccountState, Integer> {}
}
