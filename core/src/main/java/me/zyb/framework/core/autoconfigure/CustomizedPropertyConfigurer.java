package me.zyb.framework.core.autoconfigure;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 动态读取配置文件（.properties/.yml等）的信息
 * @author zhangyingbin
 *
 */
public class CustomizedPropertyConfigurer extends PropertyPlaceholderConfigurer{
	
	private static Map<String, Object> contextPropertiesMap;
	
	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
		super.processProperties(beanFactoryToProcess, props);
		contextPropertiesMap = new HashMap<String, Object>();
		for(Object key : props.keySet()){
			String keyStr = key.toString();
			Object value = props.getProperty(keyStr);
			contextPropertiesMap.put(keyStr, value);
		}
	}

	public static Object getProperty(String key){
		return contextPropertiesMap.get(key);
	}
	
	public static String getString(String key){
		return contextPropertiesMap.get(key).toString();
	}
	
	public static int getInt(String key){
		return Integer.parseInt(getString(key));
	}
	
	public static long getLong(String key){
		return Long.parseLong(getString(key));
	}
	
	public static double getDouble(String key){
		return Double.parseDouble(getString(key));
	}
	
	public static BigDecimal getBigDecimal(String key){
		return new BigDecimal(getString(key));
	}
}
