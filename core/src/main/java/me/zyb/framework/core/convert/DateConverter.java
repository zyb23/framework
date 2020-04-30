package me.zyb.framework.core.convert;


import me.zyb.framework.core.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.Date;

/**
 * @author zhangyingbin
 */
public final class DateConverter implements Converter<String, Date> {
	@Override
	public Date convert(String source) {
		if(null == source){
			return null;
		}
		String value = source.trim();
		if(StringUtils.isBlank(value)){
			return null;
		}
		if(source.matches("^\\d{4}-\\d{1,2}$")){
			return DateUtil.parse(value, DateUtil.FORMAT_MONTH);
		}else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")){
			return DateUtil.parse(value, DateUtil.FORMAT_DAY);
		}else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{1,2}$")){
			return DateUtil.parse(value, DateUtil.FORMAT_MINUTE);
		}else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{1,2}:\\d{1,2}$")){
			return DateUtil.parse(value, DateUtil.FORMAT_DATE_TIME);
		}else {
			throw new IllegalArgumentException("Invalid boolean value '" + source + "'");
		}
	}
}
