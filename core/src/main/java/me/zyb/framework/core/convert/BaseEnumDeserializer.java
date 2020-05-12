package me.zyb.framework.core.convert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import me.zyb.framework.core.base.BaseEnum;
import org.springframework.beans.BeanUtils;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * json 枚举转化（反序列化）
 * @author zhangyingbin
 */
@Slf4j
public class BaseEnumDeserializer extends JsonDeserializer<BaseEnum> implements ObjectDeserializer {

	@SuppressWarnings("unchecked")
	@Override
	public BaseEnum deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
		JsonNode node = jp.getCodec().readTree(jp);
		//枚举的参数名
		String propertyName = jp.currentName();
		//枚举参数所在的对象
		Object currentValue = jp.getCurrentValue();
		Class enumType = BeanUtils.findPropertyType(propertyName, currentValue.getClass());

		JSONObject jb = null;
		try {
			jb = JSON.parseObject(node.toString());
		} catch (Exception ignored){
		}
		String value = null == jb ? node.asText() : jb.getString("value");

		return (BaseEnum) BaseEnum.getEnum(value, enumType);
	}

	@Override
	public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
		JSONObject jb = null;
		try {
			jb = parser.parseObject();
		} catch (Exception ignored){
		}
		String value = null == jb ? parser.parse().toString() : jb.getString("value");
		return (T) BaseEnum.getEnum(value, (Class)type);
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
