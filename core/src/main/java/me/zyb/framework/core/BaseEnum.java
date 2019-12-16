package me.zyb.framework.core;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 枚举基类
 * @param <Y> 数据库存储的Java类型（value）
 * @author zhangyingbin
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public interface BaseEnum<Y> {

	/**
	 * 获取数据库存储值（value）
	 * @return Y
	 */
	public Y getValue();

	/**
	 * 获取编码
	 * @return String
	 */
	public String getCode();

	/**
	 * 获取名称
	 * @return String
	 */
	public String getName();
}
