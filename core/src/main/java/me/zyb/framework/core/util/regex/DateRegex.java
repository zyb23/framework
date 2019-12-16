package me.zyb.framework.core.util.regex;

import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 日期时间正则校验
 * @author zhangyingbin
 *
 */
@Slf4j
public class DateRegex {
	private static Pattern pattern;
	private static Matcher matcher;
	
	/** 间隔符<br>	 -<br>/<br>.<br>_
	 */
	private static final String REGEX_SPACE_MARK = "([-\\/\\._])";
	
	/** 年<br>不匹配：0、00、000、…… */
	private static final String REGEX_YEAR = "([0-9]*[1-9][0-9]*)";
	/** 闰年 */
	private static final String REGEX_LEAP_YEAR = "0*[48]|\\d*(([13579][26]|[2468][048])|([13579][26]|[2468][048])0{2}|[2468]0{3}|0{4,})";
	
	/** 月（大）1、3、5、7、8、10、12 */
	private static final String REGEX_MONTH_BIG = "(0?[13578]|1[02])";
	/** 月（小）4、6、9、10 */
	private static final String REGEX_MONTH_SMALL = "(0?[469]|11)";
	/** 月（二月） */
	private static final String REGEX_MONTH_FEB = "(0?2)";
	
	/** 日（31天）*/
	private static final String REGEX_DAY_BIG = "(0?[1-9]|[12]\\d|3[01])";
	/** 日（30天）*/
	private static final String REGEX_DAY_SMALL = "(0?[1-9]|[12]\\d|30)";
	/** 日（28天）*/
	private static final String REGEX_DAY_FEB_28S = "(0?[1-9]|1\\d|2[0-8])";
	/** 日（29日）*/
	private static final String REGEX_DAY_FEB_29 = "(29)";
	
	/** 月-日（月大）*/
	private static final String REGEX_MONTH_DAY_BIG = "(" + REGEX_MONTH_BIG + REGEX_SPACE_MARK + REGEX_DAY_BIG + ")";
	/** 月-日（月大）（没有间隔符）*/
	private static final String REGEX_MONTH_DAY_BIG_WITHOUT_SPACE_MARK = "(" + REGEX_MONTH_BIG + REGEX_DAY_BIG + ")";
	/** 月-日（月小）*/
	private static final String REGEX_MONTH_DAY_SMALL = "(" + REGEX_MONTH_SMALL + REGEX_SPACE_MARK + REGEX_DAY_SMALL + ")";
	/** 月-日（月小）（没有间隔符）*/
	private static final String REGEX_MONTH_DAY_SMALL_WITHOUT_SPACE_MARK = "(" + REGEX_MONTH_SMALL + REGEX_DAY_SMALL + ")";
	/** 月-日（二月28天）*/
	private static final String REGEX_MONTH_DAY_FEB_28S = "(" + REGEX_MONTH_FEB + REGEX_SPACE_MARK + REGEX_DAY_FEB_28S + ")";
	/** 月-日（二月28天）（没有间隔符）*/
	private static final String REGEX_MONTH_DAY_FEB_28S_WITHOUT_SPACE_MARK = "(" + REGEX_MONTH_FEB + REGEX_DAY_FEB_28S + ")";
	/** 月-日（二月29号）*/
	private static final String REGEX_MONTH_DAY_FEB_29 = "(" + REGEX_MONTH_FEB + REGEX_SPACE_MARK + REGEX_DAY_FEB_29 + ")";
	/** 月-日（二月29号）（没有间隔符）*/
	private static final String REGEX_MONTH_DAY_FEB_29_WITHOUT_SPACE_MARK = "(" + REGEX_MONTH_FEB + REGEX_DAY_FEB_29 + ")";
	/** 月-日<br>不包括二月29号 */
	private static final String REGEX_MONTH_DAY_OUT_OF_FEB_29 = "(" + REGEX_MONTH_DAY_BIG + "|" + REGEX_MONTH_DAY_SMALL + "|" + REGEX_MONTH_DAY_FEB_28S + ")";
	/** 月-日<br>不包括二月29号（没有间隔符） */
	private static final String REGEX_MONTH_DAY_OUT_OF_FEB_29_WITHOUT_SPACE_MARK = "(" + REGEX_MONTH_DAY_BIG_WITHOUT_SPACE_MARK
																					+ "|" + REGEX_MONTH_DAY_SMALL_WITHOUT_SPACE_MARK
																					+ "|" + REGEX_MONTH_DAY_FEB_28S_WITHOUT_SPACE_MARK + ")";
	
	/** 时（HH）*/
	private static final String REGEX_HOUR = "([01]?\\d|2[0-3])";
	/** 分（mm）*/
	private static final String REGEX_MINUTE = "([0-5]?\\d)";
	/** 秒（ss）*/
	private static final String REGEX_SECOND = "([0-5]?\\d)";
	/** 时间<br> HH:mm:ss */
	private static final String REGEX_TIME = "(" + REGEX_HOUR + ":" + REGEX_MINUTE + ":" + REGEX_SECOND + ")";
	
	/** 日期 */
	private static final String REGEX_COMMON_DATE = "((" + REGEX_YEAR + REGEX_SPACE_MARK + REGEX_MONTH_DAY_OUT_OF_FEB_29 + ")"
												+ "|" + "(" + REGEX_LEAP_YEAR + REGEX_SPACE_MARK + REGEX_MONTH_DAY_FEB_29 + "))";
	/** 日期（没有间隔符）<br>
	 * 如：19900225（1990年2月25日）
	 */
	private static final String REGEX_COMMON_DATE_WITHOUT_SPACE_MARK = "((" + REGEX_YEAR + REGEX_MONTH_DAY_OUT_OF_FEB_29_WITHOUT_SPACE_MARK + ")"
												+ "|" + "(" + REGEX_LEAP_YEAR + REGEX_MONTH_DAY_FEB_29_WITHOUT_SPACE_MARK + "))";
	/** 日期时间<br>
	 * yyyy-MM-dd HH:mm:ss类型的字符串（“-”有可能是其他符号REGEX_SPACE_MARK）<br>
	 * 没有时间部分也可匹配
	 */
	private static final String REGEX_COMMON_DATETIME = REGEX_COMMON_DATE + "( "+ REGEX_TIME +")?";
	
	/**
	 * 日期<br>
	 * yyyy-MM-dd<br>
	 * yyyy/MM/dd<br>
	 * yyyy.MM.dd<br>
	 * yyyy_MM_dd<br>
	 * @author zhangyingbin
	 *
	 * @param date  日期
	 * @return boolean
	 */
	public static boolean isDate(String date){
		log.debug("matcher pattern : " + REGEX_COMMON_DATE + ", source : " + date);
		
		pattern = Pattern.compile(REGEX_COMMON_DATE);
		matcher = pattern.matcher(date);
		
		return matcher.matches(); 
	}
	
	/**
	 * 日期<br>
	 * yyyyMMdd
	 * @author zhangyingbin
	 *
	 * @param date  日期
	 * @return boolean
	 */
	public static boolean isDateWithoutSpaceMark(String date){
		log.debug("matcher pattern : " + REGEX_COMMON_DATE_WITHOUT_SPACE_MARK + ", source : " + date);
		
		pattern = Pattern.compile(REGEX_COMMON_DATE_WITHOUT_SPACE_MARK);
		matcher = pattern.matcher(date);
		
		return matcher.matches();
	}
	
	/**
	 * 日期时间<br>
	 * yyyy-MM-dd HH:mm:ss<br>
	 * yyyy/MM/dd HH:mm:ss<br>
	 * yyyy.MM.dd HH:mm:ss<br>
	 * yyyy_MM_dd HH:mm:ss<br>
	 * @author zhangyingbin
	 *
	 * @param dateTime  日期时间
	 * @return boolean
	 */
	public static boolean isDateTime(String dateTime){
		log.debug("matcher pattern : " + REGEX_COMMON_DATETIME + ", source : " + dateTime);
		
		pattern = Pattern.compile(REGEX_COMMON_DATETIME);
		matcher = pattern.matcher(dateTime);
		
		return matcher.matches(); 
	}
	
	/**
	 * 是否闰年
	 * @author zhangyingbin
	 *
	 * @param year 年份
	 * @return boolean
	 */
	public static boolean isLeapYear(String year){
		log.debug("matcher pattern : " + REGEX_LEAP_YEAR + ", source : " + year);
		
		pattern = Pattern.compile(REGEX_LEAP_YEAR);
		matcher = pattern.matcher(year);
		
		return matcher.matches();
	}
	
	/**
	 * 时间格式是否正确<br>
	 * HH:mm:ss
	 * @author zhangyingbin
	 *
	 * @param time  时间
	 * @return boolean
	 */
	public static boolean isTime(String time){
		log.debug("matcher pattern : " + REGEX_TIME + ", source : " + time);
		
		pattern = Pattern.compile(REGEX_TIME);
		matcher = pattern.matcher(time);
		
		return matcher.matches();
	}
}
