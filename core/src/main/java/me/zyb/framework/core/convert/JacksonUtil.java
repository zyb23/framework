package me.zyb.framework.core.convert;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * JSON字符串与Object互转工具类
 * @author zhangyingbin
 */
@Slf4j
public class JacksonUtil {
	private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	/**
	 * <p>使用泛型方法，把jsonString字符串转换为相应的javaBean对象</p>
	 * <p>1、转换为普通javaBean：readValue(jsonString, T.class)</p>
	 * <p>2、转换为List：readValue(jsonString, T[].class)得到数组，然后再将数组转化为特定类型的List</p>
	 * @param jsonString    jsonString字符串
	 * @param valueType     javaBean对象class
	 * @param <T>           javaBean对象的类型
	 * @return T
	 */
	public static <T> T readValue(String jsonString, Class<T> valueType){
		try{
			return OBJECT_MAPPER.readValue(jsonString, valueType);
		} catch (Exception e){
			log.error("jsonString转javaBean异常", e);
		}
		return null;
	}

	/**
	 * <p>使用泛型方法，把jsonString字符串转换为相应的javaBean对象</p>
	 * <p>1、转换为普通javaBean：readValue(jsonString, new TypeReference<T>())</p>
	 * <p>2、转换为List：readValue(jsonString, new TypeReference<List<T>>())，List为特定的集合类型</p>
	 * @param jsonString            jsonString字符串
	 * @param valueTypeReference    javaBean对象的类型引用
	 * @param <T>                   javaBean对象的类型
	 * @return T
	 */
	public static <T> T readValue(String jsonString, TypeReference<T> valueTypeReference){
		try{
			return OBJECT_MAPPER.readValue(jsonString, valueTypeReference);
		} catch (Exception e){
			log.error("jsonString转javaBean异常", e);
		}
		return null;
	}

	/**
	 * javaBean转换为jsonString字符串
	 * @param object
	 * @return
	 */
	public static String toJsonString(Object object){
		try{
			return OBJECT_MAPPER.writeValueAsString(object);
		} catch (Exception e){
			log.error("javaBean转jsonString异常", e);
		}
		return null;
	}
}
