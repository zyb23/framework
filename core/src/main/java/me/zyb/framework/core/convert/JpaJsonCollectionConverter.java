package me.zyb.framework.core.convert;

import com.alibaba.fastjson.JSON;
import me.zyb.framework.core.constant.SuppressWarningsKey;

import javax.persistence.AttributeConverter;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

/**
 * 数据库（Json数组）-实体（List）转换类
 * @author zhangyingbin
 */
public class JpaJsonCollectionConverter<T> implements AttributeConverter<Collection<T>, String> {

	@SuppressWarnings(SuppressWarningsKey.UNCHECKED)
	public Class<T> getTClass() {
		return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Override
	public String convertToDatabaseColumn(Collection<T> attribute) {
		return JSON.toJSONString(attribute);
	}

	@Override
	public List<T> convertToEntityAttribute(String dbData) {
		return JSON.parseArray(dbData, getTClass());
	}
}
