package me.zyb.framework.core.autoconfigure;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;

import java.util.Locale;

/**
 * 消息帮助类
 * @author zhangyingbin
 *
 */
public class MessageSourceHelper {
	
	private static MessageSource messageSource;

	public MessageSourceHelper(MessageSource messageSource){
		MessageSourceHelper.messageSource = messageSource;
	}
	
	public static String getMessage(String code) {
		return messageSource.getMessage(code, null, null, null);
	}

	public static String getMessage(String code, Object[] args) {
		return messageSource.getMessage(code, args, null);
	}

	public static String getMessage(String code, Locale locale) {
		return messageSource.getMessage(code, null, null, locale);
	}
	
	public static String getMessage(String code, Object[] args, Locale locale) {
		return messageSource.getMessage(code, args, locale);
	}
	
	public static String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
		return messageSource.getMessage(code, args, defaultMessage, locale);
	}
	
	public static String getMessage(MessageSourceResolvable resolvable, Locale locale) {
		return messageSource.getMessage(resolvable, locale);
	}

	public static MessageSource getMessageSource() {
		return messageSource;
	}

	public static void setMessageSource(MessageSource messageSource) {
		MessageSourceHelper.messageSource = messageSource;
	}
}
