package me.zyb.framework.core.util.regex;

import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串正则校验
 * @author zhangyingbin
 *
 */
@Slf4j
public class StringRegex {
	private static Pattern pattern;
	private static Matcher matcher;
	
	/** 字母/数字/混合 */
	private static final String REGEX_ALPHABET_OR_NUMBER = "[A-Za-z0-9]+";
	/** 必须是字母数字的混合 */
	private static final String REGEX_ALPHABET_AND_NUMBER = "(?!^\\d+$)(?!^[a-zA-Z]+$)[0-9a-zA-Z]+";
	
	
	/**
	 * 校验字符串是否符合正则表达式
	 * @author zhangyingbin
	 * @param regex 正则表达式
	 * @param str 待校验串
	 * @return boolean
	 */
	public static boolean regex(String regex, String str){
		log.debug("matcher pattern : " + regex + ", source : " + str);
		
		pattern = Pattern.compile(regex);
		matcher = pattern.matcher(str);
		
		return matcher.matches();
	}
	
	/**
	 * 字母/数字/混合
	 * @author zhangyingbin
	 * @param str 待校验串
	 * @return boolean
	 */
	public static boolean isAlphabetOrNumber(String str){
		log.debug("matcher pattern : " + REGEX_ALPHABET_OR_NUMBER + ", source : " + str);
		
		pattern = Pattern.compile(REGEX_ALPHABET_OR_NUMBER);
		matcher = pattern.matcher(str);
		
		return matcher.matches();
	}
	
	/**
	 * 必须是字母数字的混合
	 * @author zhangyingbin
	 * @param str 待校验串
	 * @return boolean
	 */
	public static boolean isAlphabetAndNumber(String str){
		log.debug("matcher pattern : " + REGEX_ALPHABET_AND_NUMBER + ", source : " + str);
		
		pattern = Pattern.compile(REGEX_ALPHABET_AND_NUMBER);
		matcher = pattern.matcher(str);
		
		return matcher.matches();
	}
	
	/**
	 * 检验字符串的长度（任何可见字符）
	 * @author zhangyingbin
	 * @param n 最小长度（正数）
	 * @param m 最大长度（正数）
	 * @param str 待校验串
	 * @return boolean
	 */
	public static boolean checkLength(int n, int m, String str){
		String regex = "\\S{" + n + "," + m + "}";
		log.debug("matcher pattern : " + regex + ", source : " + str);
		
		pattern = Pattern.compile(regex);
		matcher = pattern.matcher(str);
		
		return matcher.matches();
	}

	/**
	 * 是否QQ号
	 * @param qqNumber  qq号
	 * @return boolean
	 */
	public static boolean isQQNumber(String qqNumber){
		String regex = "([1-9]\\d{4,10})";
		log.debug("matcher pattern : " + regex + ", source : " + qqNumber);
		
		pattern = Pattern.compile(regex);
		matcher = pattern.matcher(qqNumber);
		
		return matcher.matches();
	}
}
