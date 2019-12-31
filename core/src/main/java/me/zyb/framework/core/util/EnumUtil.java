package me.zyb.framework.core.util;

import me.zyb.framework.core.base.BaseEnum;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zhangyingbin
 */
public class EnumUtil {
	/**
	 * 根据输入值转换成对应的枚举
	 * @param source    输入值
	 * @param enumType  枚举类的class
	 * @param <T>       枚举类
	 * @return Object
	 */
	public static <T extends BaseEnum<String>> Object getEnum(String source, Class<T> enumType){
		if(StringUtils.isBlank(source)){
			return null;
		}
		for(T enumObj : enumType.getEnumConstants()){
			if(source.equals(enumObj.getValue())){
				return enumObj;
			}
		}
		return null;
	}

	/**
	 * 根据输入值转换成对应的枚举
	 * @param source    输入值
	 * @param enumType  枚举类的class
	 * @param <T>       枚举类
	 * @return Object
	 */
	public static <T extends BaseEnum<Long>> Object getEnum(Long source, Class<T> enumType){
		if(null == source){
			return null;
		}
		for(T enumObj : enumType.getEnumConstants()){
			if(source.equals(enumObj.getValue())){
				return enumObj;
			}
		}
		return null;
	}

	/**
	 * 根据输入值转换成对应的枚举
	 * @param source    输入值
	 * @param enumType  枚举类的class
	 * @param <T>       枚举类
	 * @return Object
	 */
	public static <T extends BaseEnum<Integer>> Object getEnum(Integer source, Class<T> enumType){
		if(null == source){
			return null;
		}
		for(T enumObj : enumType.getEnumConstants()){
			if(source.equals(enumObj.getValue())){
				return enumObj;
			}
		}
		return null;
	}
}
