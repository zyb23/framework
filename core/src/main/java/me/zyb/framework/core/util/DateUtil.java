package me.zyb.framework.core.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.FastDateFormat;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日间工具类
 * @author zhangyingbin
 *
 */
@Slf4j
public class DateUtil {
	/** yyyy */
	public static final String FORMAT_YEAR = "yyyy";
	/** yyyy-MM */
	public static final String FORMAT_MONTH = "yyyy-MM";
	/** yyyy-MM-dd */
	public static final String FORMAT_DAY = "yyyy-MM-dd";
	/** yyyy-MM-dd HH */
	public static final String FORMAT_HOUR = "yyyy-MM-dd HH";
	/** yyyy-MM-dd HH:mm */
	public static final String FORMAT_MINUTE = "yyyy-MM-dd HH:mm";
	/** yyyy-MM-dd HH:mm:ss */
	public static final String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
	/** yyyy-MM-dd HH:mm:ss.S */
	public static final String FORMAT_TIMESTAMP = "yyyy-MM-dd HH:mm:ss.S";
	/** HH:mm:ss */
	public static final String FORMAT_TIME = "HH:mm:ss";
	/** GMT+08:00 */
	public static final String TIMEZONE = "GMT+08:00";
	/** 00:00:00*/
	public static final String START_TIME = " 00:00:00";
	/** 59:59:59*/
	public static final String END_TIME = " 23:59:59";
	/** yyyy */
	public static final FastDateFormat FDF_YEAR = FastDateFormat.getInstance(FORMAT_YEAR);
	/** yyyy-MM */
	public static final FastDateFormat FDF_MONTH = FastDateFormat.getInstance(FORMAT_MONTH);
	/** yyyy-MM-dd */
	public static final FastDateFormat FDF_DAY = FastDateFormat.getInstance(FORMAT_DAY);
	/** yyyy-MM-dd HH */
	public static final FastDateFormat FDF_HOUR = FastDateFormat.getInstance(FORMAT_HOUR);
	/** yyyy-MM-dd HH:mm */
	public static final FastDateFormat FDF_MINUTE = FastDateFormat.getInstance(FORMAT_MINUTE);
	/** yyyy-MM-dd HH:mm:ss */
	public static final FastDateFormat FDF_DATE_TIME = FastDateFormat.getInstance(FORMAT_DATE_TIME);
	/** HH:mm:ss */
	public static final FastDateFormat FDF_TIME = FastDateFormat.getInstance(FORMAT_TIME);

	/** 日期类型  */
	public static final String[] DATE_TYPE = new String[]{"DAY", "WEEK", "MONTH", "YEAR"};

	/**
	 * 某一周的所有日期
	 */
	@Data
	class Week {
		/**
		 * 星期一
		 */
		private Date monday;
		/**
		 * 星期二
		 */
		private Date tuesday;
		/**
		 * 星期三
		 */
		private Date wednesday;
		/**
		 * 星期四
		 */
		private Date thursday;
		/**
		 * 星期五
		 */
		private Date friday;
		/**
		 * 星期六
		 */
		private Date saturday;
		/**
		 * 星期日
		 */
		private Date sunday;
	}

	@Data
	class Month {
		/**
		 * 月头（1号）
		 */
		/** 每月的头尾日期 */
		private Date firstDay;
		/**
		 * 月尾
		 */
		private Date lastDay;
	}
	
	/**
	 * 判断一个日期是不是今天
	 * @author zhangyingbin
	 * @param date  日期
	 * @return boolean
	 */
	public static boolean isToday(Date date){
		String todayDate = FDF_DAY.format(new Date());
		String strDate = FDF_DAY.format(date);
		if(todayDate.equals(strDate)){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 指定时间间隔second秒后的时间（负数向前，正数向后）
	 * @author zhangyingbin
	 * @param date      时间
	 * @param second    秒数
	 * @return Date
	 */
	public static Date differSecond(Date date, int second) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(null == date ? new Date() : date);
		
		long longMills = cal.getTimeInMillis() + second * 1000;
		cal.setTimeInMillis(longMills);
		
		return cal.getTime();
	}
	
	
	/**
	 * 指定时间间隔minute分钟后的时间（负数向前，正数向后）
	 * @author zhangyingbin
	 * @param date      时间
	 * @param minute    分钟数
	 * @return Date
	 */
	public static Date differMinute(Date date, int minute) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(null == date ? new Date() : date);

		long longMills = cal.getTimeInMillis() + minute * 60 * 1000;
		cal.setTimeInMillis(longMills);
		
		return cal.getTime();
	}

	/**
	 * 指定时间间隔hour小时后的时间（负数向前，正数向后）
	 * @author zhangyingbin
	 * @param date  时间
	 * @param hour  小时数
	 * @return Date
	 */
	public static Date differHour(Date date, int hour) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(null == date ? new Date() : date);
		
		long longMills = cal.getTimeInMillis() + hour * 60 * 60 * 1000;
		cal.setTimeInMillis(longMills);

		return cal.getTime();
	}

	/**
	 * 指定时间间隔day天后的时间（负数向前，正数向后）
	 * @author zhangyingbin
	 * @param date  时间
	 * @param day   天数
	 * @return Date
	 */
	public static Date differDay(Date date, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(null == date ? new Date() : date);
		
		int today = cal.get(Calendar.DATE);
		cal.set(Calendar.DATE, today + day);
		
		return cal.getTime();
	}

	/**
	 * 指定时间间隔week星期后的时间（负数向前，正数向后）
	 * @author zhangyingbin
	 * @param date  时间
	 * @param week  周数
	 * @return Date
	 */
	public static Date differWeek(Date date, int week) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(null == date ? new Date() : date);

		long longMills = cal.getTimeInMillis() + (long)week * 7 * 24 * 60 * 60 * 1000;
		cal.setTimeInMillis(longMills);
		
		return cal.getTime();
	}

	/**
	 * 指定日期间隔month月后的时间（负数向前，正数向后）
	 * @author zhangyingbin
	 * @param date      时间
	 * @param month     月数
	 * @return Date
	 */
	public static Date differMonth(Date date, int month) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(null == date ? new Date() : date);
		
		int today = cal.get(Calendar.MONTH);
		cal.set(Calendar.MONTH, today + month);
		
		return cal.getTime();
	}

	/**
	 * 指定日期间隔year年后的时间（负数向前，正数向后）
	 * @author zhangyingbin
	 * @param date  日期
	 * @param year  年数
	 * @return Date
	 */
	public static Date differYear(Date date, int year) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(null == date ? new Date() : date);
		
		int today = cal.get(Calendar.YEAR);
		cal.set(Calendar.YEAR, today + year);
		
		return cal.getTime();
	}
	
	/**
	 * 获取每周的日期（包括本周）
	 * 
	 * @author zhangyingbin
	 * @param date 某个日期，从这个日期所属的周开始计算（为null则默认当天）
	 * @param value 正数表示后几周，负数表示前几周，0表示只获取本周
	 * @return List<Week>
	 */
	public static List<Week> getWeekDay(Date date, int value){
		List<Week> list = new ArrayList<Week>();
		//获取当前日期
		Calendar calendar = Calendar.getInstance();
		if(null != date){
			calendar.setTime(date);
		}
		//日期置回周一
		while(calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY){
			calendar.add(Calendar.DATE, -1);
		}
		int min, max;
		if(value < 0){
			min = value;
			max = 0;
		} else {
			min = 0;
			max = value;
		}
		calendar.add(Calendar.DATE, 7 * min);
		for(int i = min; i <= max; i++){
			Week week = new DateUtil().new Week();

			week.setMonday(calendar.getTime());

			calendar.add(Calendar.DATE, 1);
			week.setTuesday(calendar.getTime());

			calendar.add(Calendar.DATE, 1);
			week.setWednesday(calendar.getTime());

			calendar.add(Calendar.DATE, 1);
			week.setThursday(calendar.getTime());

			calendar.add(Calendar.DATE, 1);
			week.setFriday(calendar.getTime());

			calendar.add(Calendar.DATE, 1);
			week.setSaturday(calendar.getTime());

			calendar.add(Calendar.DATE, 1);
			week.setSunday(calendar.getTime());

			calendar.add(Calendar.DATE, 1);

			list.add(week);
		}
		
		return list;
	}
	
	/**
	 * 获取每月的头尾日期（包括本月）
	 * 
	 * @author zhangyingbin
	 * @param date 某个日期，从这个日期所属的月开始计算（为null则默认当天）
	 * @param value 正数表示后几月，负数表示前几月，0表示只获取本月
	 * @return List<Month>
	 */
	public static List<Month> getMonthDay(Date date, int value){
		List<Month> list = new ArrayList<Month>();
		//获取当前日期
		Calendar calendar = Calendar.getInstance();
		if(null != date){
			calendar.setTime(date);
		}
		int min, max;
		if(value < 0){
			min = value;
			max = 0;
		} else {
			min = 0;
			max = value;
		}
		for(int i = min; i <= max; i++){
			Month month = new DateUtil().new Month();
			calendar.add(Calendar.MONTH, i);
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
			month.setFirstDay(calendar.getTime());

			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			month.setLastDay(calendar.getTime());
			list.add(month);
		}
		
		return list;
	}
	
	/**
	 * 两个日期相差的时间毫秒数
	 * @author zhangyingbin
	 * @param start 开始时间
	 * @param end   结束时间
	 * @return long
	 */
	public static long betweenMillisecond(Date start, Date end){
		return end.getTime() - start.getTime();
	}
	
	/**
	 * 两个日期相差的时间秒数
	 * @author zhangyingbin
	 * @param start 开始时间
	 * @param end   结束时间
	 * @return long
	 */
	public static long betweenSecond(Date start, Date end){
		long milliseconds = betweenMillisecond(start, end);

		return milliseconds / 1000;
	}
	
	/**
	 * 两个日期相差的时间分钟数
	 * @author zhangyingbin
	 * @param start 开始时间
	 * @param end   结束时间
	 * @return long
	 */
	public static long betweenMinute(Date start, Date end){
		try {
			start = FDF_MINUTE.parse(FDF_DATE_TIME.format(start));
			end = FDF_MINUTE.parse(FDF_DATE_TIME.format(end));
		} catch (ParseException e) {
			log.error(e.getMessage());
		}
		long milliseconds = betweenMillisecond(start, end);

		return milliseconds / (1000 * 60);
	}
	
	/**
	 * 两个日期相差的时间小时数
	 * @author zhangyingbin
	 * @param start 开始时间
	 * @param end   结束时间
	 * @return long
	 */
	public static long betweenHour(Date start, Date end){
		try {
			start = FDF_HOUR.parse(FDF_DATE_TIME.format(start));
			end = FDF_HOUR.parse(FDF_DATE_TIME.format(end));
		} catch (ParseException e) {
			log.error(e.getMessage());
		}
		long milliseconds = betweenMillisecond(start, end);

		return milliseconds / (1000 * 60 * 60);
	}
	
	/**
	 * 两个日期相差的时间天数
	 * @author zhangyingbin
	 * @param start 开始时间
	 * @param end   结束时间
	 * @return long
	 */
	public static long betweenDay(Date start, Date end){
		try {
			start = FDF_DAY.parse(FDF_DATE_TIME.format(start));
			end = FDF_DAY.parse(FDF_DATE_TIME.format(end));
		} catch (ParseException e) {
			log.error(e.getMessage());
		}
		long milliseconds = betweenMillisecond(start, end);

		return milliseconds / (1000 * 60 * 60 * 24);
	}

	/**
	 * 两个日期相差的时间月数
	 * @author zhangyingbin
	 * @param start 开始时间
	 * @param end   结束时间
	 * @return long
	 */
	public static long betweenMonth(Date start, Date end){
		Calendar calendarStart = Calendar.getInstance();
		calendarStart.setTime(start);
		Calendar calendarEnd = Calendar.getInstance();
		calendarEnd.setTime(end);

		long year = calendarEnd.get(Calendar.YEAR) - calendarStart.get(Calendar.YEAR);
		long month = calendarEnd.get(Calendar.MONTH) - calendarStart.get(Calendar.MONTH);

		return year * 12 + month;
	}

	/**
	 * 两个日期相差的时间年数
	 * @author zhangyingbin
	 * @param start 开始时间
	 * @param end   结束时间
	 * @return long
	 */
	public static long betweenYear(Date start, Date end){
		Calendar calendarStart = Calendar.getInstance();
		calendarStart.setTime(start);
		Calendar calendarEnd = Calendar.getInstance();
		calendarEnd.setTime(end);

		return calendarEnd.get(Calendar.YEAR) - calendarStart.get(Calendar.YEAR);
	}
	
	/**
	 * Date类型序列化
	 * @author zhangyingbin
	 *
	 */
	public class DateJsonSerializer extends JsonSerializer<Date> {
		@Override
		public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
				throws IOException {
			jsonGenerator.writeString(FDF_DATE_TIME.format(date));
		}
	}
	
	/**
	 * Date类型反序列化
	 * @author zhangyingbin
	 *
	 */
	public class DateJsonDeserializer extends JsonDeserializer<Date>{
		@Override
		public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
				throws IOException {
			try {
				return FDF_DATE_TIME.parse(jsonParser.getText());
			} catch (ParseException e) {
				return new Date(jsonParser.getLongValue());
			}
		}		
	}

	/**
	 * String转Date
	 * @param dateStr   时间字符串
	 * @param format    格式
	 * @return Date
	 */
	public static Date parse(String dateStr, String format) {
		Date date = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat(format);
			date = dateFormat.parse(dateStr);
		} catch (Exception ignored) {
		}
		return date;
	}

	/**
	 * Date转String
	 * @param date      时间
	 * @param format    格式
	 * @return String
	 */
	public static String format(Date date, String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}
}
