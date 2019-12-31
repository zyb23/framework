package me.zyb.framework.core.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.StringUtils;

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

	/**
	 * 根据前端输入值转换成对应的枚举
	 * @param source    输入值
	 * @param enumType  枚举类的class
	 * @param <T>       枚举类
	 * @return Object
	 */
	public static <T extends BaseEnum> T getEnum(String source, Class<T> enumType){
		if(StringUtils.isBlank(source)){
			return null;
		}
		return getByValue(source, enumType);
	}

	/**
	 * 根据 value 值转换成对应的枚举
	 * @param value     value值
	 * @param enumType  枚举类的class
	 * @return T
	 */
	public static <T extends BaseEnum> T getByValue(Object value, Class<T> enumType){
		if(null == value){
			return null;
		}
		for(T enumObj : enumType.getEnumConstants()){
			if(String.valueOf(value).equals(String.valueOf(enumObj.getValue()))){
				return enumObj;
			}
		}
		return null;
	}

	/**
	 * 根据 code 值转换成对应的枚举
	 * @param code      code值
	 * @param enumType  枚举类的class
	 * @return T
	 */
	public static <T extends BaseEnum> T getByCode(String code, Class<T> enumType){
		if(null == code){
			return null;
		}
		for(T enumObj : enumType.getEnumConstants()){
			if(code.equals(enumObj.getCode())){
				return enumObj;
			}
		}
		return null;
	}

	/**
	 * 根据 name 值转换成对应的枚举
	 * @param name      name值
	 * @param enumType  枚举类的class
	 * @return T
	 */
	public static <T extends BaseEnum> T getByName(String name, Class<T> enumType){
		if(null == name){
			return null;
		}
		for(T enumObj : enumType.getEnumConstants()){
			if(name.equals(enumObj.getName())){
				return enumObj;
			}
		}
		return null;
	}
}
