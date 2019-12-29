package me.zyb.framework.core.convert;

import me.zyb.framework.core.base.BaseEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * <pre>
 *     Web交互通用枚举转换工场
 *     适用实现BaseEnum（implements BaseEnum）的枚举类
 *     枚举实现多接口时，BaseEnum必须第一个
 *     使用时addConverterFactory(new UniversalEnumConverterFactory());
 * </pre>>
 * @author zhangyingbin
 */
public class UniversalEnumConverterFactory implements ConverterFactory<String, BaseEnum> {
	private static final Map<Class, Converter> CLASS_CONVERTER_MAP = new WeakHashMap<Class, Converter>();

	@SuppressWarnings("unchecked")
	@Override
	public <T extends BaseEnum> Converter<String, T> getConverter(Class<T> targetType) {
		Converter converter = CLASS_CONVERTER_MAP.get(targetType);
		if(null == converter){
			converter = new IntegerToEnum(targetType);
			CLASS_CONVERTER_MAP.put(targetType, converter);
		}
		return converter;
	}

	/**
	 * 前端传入的String是个Integer数字
	 * @param <T>
	 */
	private class IntegerToEnum<T extends BaseEnum> implements Converter<String, T> {
		/** 枚举类的class */
		private Class<T> enumType;

		private IntegerToEnum(Class<T> enumType) {
			this.enumType = enumType;
		}

		@SuppressWarnings("unchecked")
		@Override
		public T convert(String source) {
			return (T) UniversalEnumConverterFactory.getEnum(source, this.enumType);
		}
	}

	/**
	 * 根据输入值转换成对应的枚举
	 * @param source    输入值
	 * @param enumType  枚举类的class
	 * @param <T>       枚举类
	 * @return Object
	 */
	private static <T extends BaseEnum> Object getEnum(String source, Class<T> enumType){
		if(StringUtils.isBlank(source)){
			return null;
		}
		for(T enumObj : enumType.getEnumConstants()){
			if(source.equals(String.valueOf(enumObj.getValue()))){
				return enumObj;
			}
		}
		return null;
	}
}
