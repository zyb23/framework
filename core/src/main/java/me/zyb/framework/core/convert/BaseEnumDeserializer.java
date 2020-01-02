package me.zyb.framework.core.convert;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import me.zyb.framework.core.base.BaseEnum;
import org.springframework.beans.BeanUtils;

import java.io.IOException;

/**
 * json 枚举转化（反序列化）
 * @author zhangyingbin
 */
public class BaseEnumDeserializer extends JsonDeserializer<BaseEnum> {

	@SuppressWarnings("unchecked")
	@Override
	public BaseEnum deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		JsonNode node = jp.getCodec().readTree(jp);
		//枚举的参数名
		String propertyName = jp.currentName();
		//枚举参数所在的对象
		Object currentValue = jp.getCurrentValue();
		Class enumType = BeanUtils.findPropertyType(propertyName, currentValue.getClass());

		return (BaseEnum) BaseEnum.getEnum(node.asText(), enumType);
	}
}
