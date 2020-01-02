package me.zyb.framework.core.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import me.zyb.framework.core.convert.BaseEnumDeserializer;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 枚举基类
 * 反序列化（json参数）时，支持枚举name和属性value，不支持ordinal
 * @param <Y> 数据库存储的Java类型（value）
 * @author zhangyingbin
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonDeserialize(using = BaseEnumDeserializer.class)
public interface BaseEnum<Y> extends Serializable {

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
	 * @param source    输入值（value）
	 * @param enumType  枚举类的class
	 * @param <T>       枚举类
	 * @return Object
	 */
	public static <T extends Enum<T> & BaseEnum> T getEnum(String source, Class<T> enumType){
		if(StringUtils.isBlank(source)){
			return null;
		}
		T valueOf = getByValue(source, enumType);
		if(null == valueOf){
			valueOf = valueOf(source, enumType);
		}
		return valueOf;
	}

	/**
	 * 根据 value 属性值转换成对应的枚举
	 * @param value     value属性值
	 * @param enumType  枚举类的class
	 * @return T
	 */
	public static <T extends Enum<T> & BaseEnum> T getByValue(Object value, Class<T> enumType){
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
	 * 根据 code 属性值转换成对应的枚举
	 * @param code      code属性值
	 * @param enumType  枚举类的class
	 * @return T
	 */
	public static <T extends Enum<T> & BaseEnum> T getByCode(String code, Class<T> enumType){
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
	 * 根据 name 属性值转换成对应的枚举
	 * @param name      name属性值
	 * @param enumType  枚举类的class
	 * @return T
	 */
	public static <T extends Enum<T> & BaseEnum> T getByName(String name, Class<T> enumType){
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

	/**
	 * 根据枚举name获取枚举
	 * @param enumName  枚举name
	 * @param enumType  枚举class
	 * @return T
	 */
	public static <T extends Enum<T> & BaseEnum> T valueOf(String enumName, Class<T> enumType){
		return Enum.valueOf(enumType, enumName);
	}
}
