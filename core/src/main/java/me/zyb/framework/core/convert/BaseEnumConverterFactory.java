package me.zyb.framework.core.convert;

import me.zyb.framework.core.base.BaseEnum;
import me.zyb.framework.core.constant.SuppressWarningsKey;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * <pre>
 *     Web交互通用枚举转换工场（json 参数无效，参考使用BaseEnumDeserializer）
 *     适用实现BaseEnum（implements BaseEnum）的枚举类
 *     枚举实现多接口时，BaseEnum必须第一个
 *     使用时addConverterFactory(new BaseEnumConverterFactory());
 * </pre>>
 * @author zhangyingbin
 */
public class BaseEnumConverterFactory implements ConverterFactory<String, BaseEnum> {
	private static final Map<Class, Converter> CLASS_CONVERTER_MAP = new WeakHashMap<Class, Converter>();

	@SuppressWarnings(SuppressWarningsKey.UNCHECKED)
	@Override
	public <T extends BaseEnum> Converter<String, T> getConverter(Class<T> targetType) {
		Converter converter = CLASS_CONVERTER_MAP.get(targetType);
		if(null == converter){
			//在Spring MVC和Spring Boot中，由于从客户端接收到的请求都被视为String类型，所以只能用String转枚举的converter
			converter = new StringToEnum(targetType);
			CLASS_CONVERTER_MAP.put(targetType, converter);
		}
		return converter;
	}

	private static class StringToEnum<T extends Enum<T> & BaseEnum> implements Converter<String, T> {
		/** 枚举类的class */
		private Class<T> enumType;

		private StringToEnum(Class<T> enumType){
			this.enumType = enumType;
		}

		@SuppressWarnings(SuppressWarningsKey.UNCHECKED)
		@Override
		public T convert(String source) {
			return BaseEnum.getEnum(source, this.enumType);
		}
	}
}
