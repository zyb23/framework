package me.zyb.framework.core.convert;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import java.lang.reflect.ParameterizedType;
import java.text.SimpleDateFormat;

/**
 * 数据库（Json）-实体（Object）转换类
 * @author zhangyingbin
 */
@Slf4j
public class JpaJsonConverter<T> implements AttributeConverter<T, String> {

	private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	public JpaJsonConverter(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		OBJECT_MAPPER.setDateFormat(sdf);
	}

	public Class<T> getTClass() {
		return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Override
	public String convertToDatabaseColumn(T attribute) {
		try {
			return OBJECT_MAPPER.writeValueAsString(attribute);
		} catch (Exception e) {
			log.error("JSON转换异常", e);
			return null;
		}
	}

	@Override
	public T convertToEntityAttribute(String dbData) {
		try {
			return OBJECT_MAPPER.readValue(dbData, getTClass());
		} catch (Exception e) {
			log.error("JSON转换异常", e);
			return null;
		}
	}
}
