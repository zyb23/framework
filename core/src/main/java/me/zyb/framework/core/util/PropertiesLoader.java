package me.zyb.framework.core.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Properties;

/**
 * @author zhangyingbin
 */
@Slf4j
public class PropertiesLoader {
	private static final ResourceLoader RESOURCE_LOADER = new DefaultResourceLoader();
	private static final Properties PROPERTIES = new Properties();
	
	public static Properties loadProperties(String... propertiesFile){
		for(String location : propertiesFile){
			log.info("Loading properties file from : " + location);
			Resource resource = RESOURCE_LOADER.getResource(location);
			try(InputStream in = resource.getInputStream()){
				PROPERTIES.load(in);
			}
			catch(Exception e){
				log.error("Loading properties file exception", e);
			}
		}
		
		return  PROPERTIES;
	}
	
	/**
	 * 返回String类型的Property
	 * @author zhangyingbin
	 * @param key   键
	 * @return String
	 */
	public static String getProperty(String key){
		String value = PROPERTIES.getProperty(key);
		if(null == value){
			throw new NoSuchElementException(key);
		}
		return value;
	}
	
	/**
	 * 返回String类型的Property，如果为null则返回defaultValue
	 * @author zhangyingbin
	 * @param key           键
	 * @param defaultValue  默认值值
	 * @return String
	 */
	public static String getProperty(String key, String defaultValue){
		return PROPERTIES.getProperty(key, defaultValue);
	}
	
	/**
	 * 返回String类型的Property
	 * @author zhangyingbin
	 * @param key       键
	 * @return String
	 */
	public static String getString(String key){
		String value = PROPERTIES.getProperty(key);
		if(null == value){
			throw new NoSuchElementException(key);
		}
		return value;
	}
	
	/**
	 * 返回String类型的Property，如果为null则返回defaultValue
	 * @author zhangyingbin
	 * @param key           键
	 * @param defaultValue  默认值
	 * @return String
	 */
	public static String getString(String key, String defaultValue){
		return PROPERTIES.getProperty(key, defaultValue);
	}
	
	/**
	 * 返回Boolean类型的Property
	 * @author zhangyingbin
	 * @param key   键
	 * @return Boolean
	 */
	public static Boolean getBoolean(String key){
		String value = PROPERTIES.getProperty(key);
		if(null == value){
			throw new NoSuchElementException(key);
		}
		return Boolean.valueOf(value);
	}
	
	/**
	 * 返回Boolean类型的Property，如果为null则返回defaultValue
	 * @author zhangyingbin
	 * @param key   键
	 * @param defaultValue 默认值
	 * @return Boolean
	 */
	public static Boolean getBoolean(String key, boolean defaultValue){
		String value = PROPERTIES.getProperty(key);
		return null != value ? Boolean.valueOf(value) : defaultValue;
	}
}
