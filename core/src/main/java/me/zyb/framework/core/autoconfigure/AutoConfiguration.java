package me.zyb.framework.core.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangyingbin
 */
@Configuration
public class AutoConfiguration {

	@Bean
	public BeanHelper beanHelper(){
		return new BeanHelper();
	}

	@Bean
	@ConditionalOnBean(MessageSource.class)
	public MessageSourceHelper messageSourceHelper(MessageSource messageSource){
		return new MessageSourceHelper(messageSource);
	}

	@Bean
	public GlobalExceptionHandler globalExceptionHandler(){
		return new GlobalExceptionHandler();
	}
}
