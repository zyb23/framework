package me.zyb.framework.usoa.dict;

import lombok.Getter;
import me.zyb.framework.core.base.BaseEnum;
import me.zyb.framework.core.convert.JpaEnumConverter;

/**
 * 地址标签
 * @author zhangyingbin
 */
@Getter
public enum AddressLabel implements BaseEnum<Integer> {
	/** 家 */
	HOME(1, "home", "家"),
	/** 公司	 */
	COMPANY(2, "company", "公司"),
	/** 学校 */
	SCHOOL(3, "school", "学校");

	private Integer value;
	private String code;
	private String name;

	AddressLabel(Integer value, String code, String name){
		this.value = value;
		this.code = code;
		this.name = name;
	}

	/**
	 * 数据库实体转换器
	 */
	public static class Converter extends JpaEnumConverter<AddressLabel, Integer> {}
}
