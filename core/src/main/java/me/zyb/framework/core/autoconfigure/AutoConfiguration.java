package me.zyb.framework.core.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

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

	@ConditionalOnMissingBean(AuditorAware.class)
	@Bean
	public DefaultAuditor defaultAuditor(){
		return new DefaultAuditor();
	}
}
