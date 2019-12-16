package me.zyb.framework.core.util.regex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 邮箱正则校验
 * @author zhangyingbin
 *
 */
public class MailRegex {
	private static final Logger logger = LoggerFactory.getLogger(MailRegex.class);
	private static Pattern pattern;
	private static Matcher matcher;
	
	/** 邮箱用户名称 */
	private static final String REGEX_USERNAME = "(([a-zA-Z0-9_-])+)";
	
	/** “@” */
	private static final String REGEX_AT = "(@)";
	
	/** 邮箱域名（xxx.xxx、xxx.xxx.xxx）*/
	private static final String REGEX_DOMAIN = "(([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2}))";
	
	/** 邮箱正则表达式 */
	private static final String REGEX = "(" + REGEX_USERNAME + REGEX_AT + REGEX_DOMAIN + ")";
	
	/**
	 * 校验邮箱是否正确
	 * @author zhangyingbin
	 * @param email 邮箱
	 * @return boolean
	 */
	public static boolean isEmail(String email){
		logger.debug("check email: " + email);
		
		if(null == email){
			return false;
		}
		pattern = Pattern.compile(REGEX);
		matcher = pattern.matcher(email);
		
		return matcher.matches();
	}
}
