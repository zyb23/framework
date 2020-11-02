package me.zyb.framework.core.util;

import me.zyb.framework.core.dict.ConstNumber;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 切面工具类
 * @author zhangyingbin
 */
public class AopUtil {

	/**
	 * 获取切点的参数（键/值对）
	 * @param joinPoint 连接点
	 * @return Map<String, Object>
	 */
	public static Map<String, Object> getParameters(JoinPoint joinPoint) {
		//参数值
		Object[] args = joinPoint.getArgs();
		ParameterNameDiscoverer pnd = new DefaultParameterNameDiscoverer();
		MethodSignature ms = (MethodSignature) joinPoint.getSignature();
		Method method = ms.getMethod();
		String[] parameterNames = pnd.getParameterNames(method);
		Map<String, Object> result = new HashMap<>(ConstNumber.INITIAL_CAPACITY);
		for (int i = 0; i < parameterNames.length; i++){
			result.put(parameterNames[i], args[i]);
		}
		return result;
	}
}
