package me.zyb.framework.core.autoconfigure;

import me.zyb.framework.core.convert.BaseEnumConverterFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zhangyingbin
 */
@Configuration
public class AutoConfiguration implements WebMvcConfigurer {

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverterFactory(new BaseEnumConverterFactory());
	}

	@ConditionalOnBean(ApplicationContext.class)
	@Bean
	public BeanHelper beanHelper(ApplicationContext applicationContext){
		return new BeanHelper(applicationContext);
	}

	@ConditionalOnMissingBean(AuditorAware.class)
	@Bean
	public DefaultAuditor defaultAuditor(){
		return new DefaultAuditor();
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
