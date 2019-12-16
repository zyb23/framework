package me.zyb.framework.core.math;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class Arith {
	private static final Logger logger = LoggerFactory.getLogger(Arith.class);
	
	/**
	 * 计算一个数的各位相加之和
	 * @author zhangyingbin
	 * @param number    数字
	 * @return Integer
	 */
	public static Integer combine(String number){
		logger.debug("求" + number + "各位相加之和！");
		Long lNumber = Long.valueOf(number);
		int length = lNumber.toString().length();
		Long combineSum = 0L;
		while(length >= 1){
			Long divisor = 1L;
			for(int i = 1; i < length; i++){
				divisor *= 10;
			}
			combineSum += lNumber / divisor;
			lNumber = lNumber % divisor;
			length--;
		}
		
		return combineSum.intValue();
	}
	
	/**
	 * 获取随机数[min, max]
	 * @author zhangyingbin
	 * @param min   最小值
	 * @param max   最大值
	 * @return Integer
	 */
	public static Integer random(int min, int max){
		Random random = new Random();
		int s = random.nextInt(max - min + 1) + min;
		return s;
	}
}
