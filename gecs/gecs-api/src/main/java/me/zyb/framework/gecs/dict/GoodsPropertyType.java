package me.zyb.framework.gecs.dict;

import lombok.Getter;
import me.zyb.framework.core.base.BaseEnum;
import me.zyb.framework.core.convert.JpaEnumConverter;

/**
 * 商品属性类型
 * @author zhangyingbin
 */
@Getter
public enum GoodsPropertyType implements BaseEnum<Integer> {
	/** 描述属性 */
	DESCRIPTION(0, "description", "描述属性"),
	/** 规格属性	 */
	STANDARD(1, "standard", "规格属性");

	private Integer value;
	private String code;
	private String name;

	GoodsPropertyType(Integer value, String code, String name){
		this.value = value;
		this.code = code;
		this.name = name;
	}

	/**
	 * 数据库实体转换器
	 */
	public static class Converter extends JpaEnumConverter<GoodsPropertyType, Integer> {}
}
