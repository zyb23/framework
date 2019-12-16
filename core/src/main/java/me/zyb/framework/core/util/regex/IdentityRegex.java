 package me.zyb.framework.core.util.regex;

import org.apache.commons.lang3.time.FastDateFormat;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 身份正则校验（身份证、护照等）
 * @author zhangyingbin
 *
 */
public class IdentityRegex {
	private static Pattern pattern;
	private static Matcher matcher;
	
	/** 15位 */
	private static final int LENGTH_FIFTEEN = 15;
	/** 18位 */
	private static final int LENGTH_EIGHTEEN = 18;
	
	/** 前6位行政区划代码 */
	private static final String REGEX_DIVISION_CODE = "([1-9]\\d{5})";
	/** 15位身份证末3位 */
	private static final String REGEX_LAST_FIFTEEN = "(\\d{3})";
	/** 18身份证位末4位 */
	private static final String REGEX_LAST_EIGHTEEN = "(\\d{3}[1-9Xx])";
	
	/**
	 * 身份证校验
	 * @author zhangyingbin
	 * @param idCard 身份证
	 * @return boolean
	 */
	public static boolean isIdCard(String idCard){
		if(null == idCard){
			return false;
		}

		//行政区划代码是否正确
		String divisionCode = idCard.substring(0, 6);
		String dateStr = "";
		String lastStr = "";
		if(!StringRegex.regex(REGEX_DIVISION_CODE, divisionCode)){
			return false;
		}
		
		if(idCard.length() == LENGTH_FIFTEEN){
			//2000年前才有15位的身份证号码
			dateStr = "19" + idCard.substring(6, 12);
			if(!DateRegex.isDateWithoutSpaceMark(dateStr)){
				return false;
			}
			lastStr = idCard.substring(12);
			if(!StringRegex.regex(REGEX_LAST_FIFTEEN, lastStr)){
				return false;
			}
		}else if(idCard.length() == LENGTH_EIGHTEEN){
			dateStr = idCard.substring(6, 14);
			if(!DateRegex.isDateWithoutSpaceMark(dateStr)){
				return false;
			}
			lastStr = idCard.substring(14);
			if(!StringRegex.regex(REGEX_LAST_EIGHTEEN, lastStr)){
				return false;
			}
		}else{
			return false;
		}
		
		return true;
	}

	/**
	 * 根据身份证号获取生日
	 * @param idCard    身份证号
	 * @return String
	 */
	public static String birthdayByIdCard(String idCard) throws Exception{
		if(isIdCard(idCard)){
			String dateStr = null;
			if(idCard.length() == LENGTH_FIFTEEN) {
				dateStr = "19" + idCard.substring(6, 12);
			}else if(idCard.length() == LENGTH_EIGHTEEN){
				dateStr = idCard.substring(6, 14);
			}
			return dateStr;
		}else{
			throw new Exception("请输入正确的身份证号！");
		}
	}

	/**
	 * 身份证校验年龄（周岁）<br>
	 * 注：返回负数表示身份证号有误
	 * @param idCard    身份证
	 * @return  int
	 */
	public static long ageByIdCard(String idCard) throws Exception{
		Long birthday = Long.parseLong(birthdayByIdCard(idCard));

		FastDateFormat fdf = FastDateFormat.getInstance("yyyyMMdd");
		Long now  = Long.parseLong(fdf.format(new Date()));

		return (now - birthday) / 10000;
	}
	
	/**
	 * 护照校验
	 * @author zhangyingbin
	 * @param passport 护照
	 * @return boolean
	 */
	public static boolean isPassport(String passport){
		return false;
	}
}
