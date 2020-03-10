package me.zyb.framework.gecs.dict;

import lombok.Getter;
import me.zyb.framework.core.base.BaseEnum;
import me.zyb.framework.core.convert.JpaEnumConverter;

/**
 * 商品属性输入方式
 * @author zhangyingbin
 */
@Getter
public enum GoodsPropertyInputMode implements BaseEnum<Integer> {
	/** 单选 */
	RADIO(0, "radio", "单选"),
	/** 多选	 */
	CHECKBOX(1, "checkbox", "多选"),
	/** 自定义	 */
	CUSTOM(2, "custom", "自定义");

	private Integer value;
	private String code;
	private String name;

	GoodsPropertyInputMode(Integer value, String code, String name){
		this.value = value;
		this.code = code;
		this.name = name;
	}

	/**
	 * 数据库实体转换器
	 */
	public static class Converter extends JpaEnumConverter<GoodsPropertyInputMode, Integer> {}
}
