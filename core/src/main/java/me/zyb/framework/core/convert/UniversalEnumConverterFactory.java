package me.zyb.framework.core.convert;

import me.zyb.framework.core.base.BaseEnum;
import me.zyb.framework.core.util.EnumUtil;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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
			//BaseEnum<Y>
			ParameterizedType type = (ParameterizedType )targetType.getGenericInterfaces()[0];
			//Y
			Type genericType = type.getActualTypeArguments()[0];
			if(Integer.class.getTypeName().equals(genericType.getTypeName())){
				converter = new IntegerToEnum<>(targetType);
			}
			else if(Long.class.getTypeName().equals(genericType.getTypeName())){
				converter = new LongToEnum<>(targetType);
			}
			else if(String.class.getTypeName().equals(genericType.getTypeName())){
				converter = new StringToEnum<>(targetType);
			}
			CLASS_CONVERTER_MAP.put(targetType, converter);
		}
		return converter;
	}

	private class IntegerToEnum<T extends BaseEnum<Integer>> implements Converter<Integer, T> {
		/** 枚举类的class */
		private Class<T> enumType;

		private IntegerToEnum(Class<T> enumType) {
			this.enumType = enumType;
		}

		@SuppressWarnings("unchecked")
		@Override
		public T convert(Integer source) {
			return (T) EnumUtil.getEnum(source, this.enumType);
		}
	}

	private class LongToEnum<T extends BaseEnum<Long>> implements Converter<Long, T> {
		/** 枚举类的class */
		private Class<T> enumType;

		private LongToEnum(Class<T> enumType) {
			this.enumType = enumType;
		}

		@SuppressWarnings("unchecked")
		@Override
		public T convert(Long source) {
			return (T) EnumUtil.getEnum(source, this.enumType);
		}
	}

	private static class StringToEnum<T extends BaseEnum<String>> implements Converter<String, T> {
		/** 枚举类的class */
		private Class<T> enumType;

		private StringToEnum(Class<T> enumType){
			this.enumType = enumType;
		}

		@SuppressWarnings("unchecked")
		@Override
		public T convert(String source) {
			return (T) EnumUtil.getEnum(source, this.enumType);
		}
	}
}
