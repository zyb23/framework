package me.zyb.framework.core.util.regex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 电话号码正则校验
 * @author zhangyingbin
 *
 */
public class PhoneRegex {
	private static final Logger logger = LoggerFactory.getLogger(PhoneRegex.class);
	private static Pattern pattern;
	private static Matcher matcher;
	
	/** 移动手机号码<br>
	 * 中国移动：134、135、136、137、138、139、147、150、151、152、157、158、159、178、182、183、187、188<br>
	 * 中国联通：130、131、132、145、155、156、176、185、186<br>
	 * 中国电信：133、153、177、180、181、189<br>
	 * 中国卫通：1349<br>
	 * 虚拟运营商：170
	 */
	private static final String REGEX_MOBILE_PHONE = "((13[0-9])|(14[57])|(15[^4\\D])|(17[0678])|(18[^4\\D]))\\d{8}";
	
	/** 固定电话 */
	private static final String REGEX_FIXED_PHONE = "((010)|(02\\d{1})|(0[3-9]{1}\\d{2}))-\\d{7,8}";
	
	
	/**
	 * 是否是移动手机号码
	 * @author zhangyingbin
	 * @param mobilePhone 手机号码
	 * @return boolean
	 */
	public static boolean isMobilePhone(String mobilePhone){
		logger.debug("matcher pattern : " + REGEX_MOBILE_PHONE + ", source : " + mobilePhone);
		
		pattern = Pattern.compile(REGEX_MOBILE_PHONE);
		matcher = pattern.matcher(mobilePhone);
		
		return matcher.matches();
	}
	
	/**
	 * 是否是固定电话
	 * @author zhangyingbin
	 * @param fixedPhone 固定电话
	 * @return boolean
	 */
	public static boolean isFixedPhone(String fixedPhone){
		logger.debug("matcher pattern : " + REGEX_FIXED_PHONE + ", source : " + fixedPhone);
		
		pattern = Pattern.compile(REGEX_FIXED_PHONE);
		matcher = pattern.matcher(fixedPhone);
		
		return matcher.matches();
	}
}
