package me.zyb.framework.core.convert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import me.zyb.framework.core.base.BaseEnum;
import org.springframework.beans.BeanUtils;

import java.io.IOException;

/**
 * json 枚举转化（反序列化）
 * @author zhangyingbin
 */
@Slf4j
public class BaseEnumDeserializer extends JsonDeserializer<BaseEnum> {

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
		} catch (Exception e){
			log.info("前端传递的是枚举值，不是枚举对象");
		}
		String value = null == jb ? node.asText() : jb.getString("value");

		return (BaseEnum) BaseEnum.getEnum(value, enumType);
	}
}
