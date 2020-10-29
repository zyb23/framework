package me.zyb.framework.core.autoconfigure;

import me.zyb.framework.core.convert.BaseEnumConverterFactory;
import me.zyb.framework.core.util.cache.RedisUtil;
import me.zyb.framework.core.util.mail.MailUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.format.FormatterRegistry;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.TemplateEngine;

/**
 * @author zhangyingbin
 */
@Configuration
public class AutoConfiguration implements WebMvcConfigurer {

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverterFactory(new BaseEnumConverterFactory());
	}

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
	public MessageSourceHelper messageSourceHelper(MessageSource messageSource){
		return new MessageSourceHelper(messageSource);
	}

	@Bean
	public GlobalExceptionHandler globalExceptionHandler(){
		return new GlobalExceptionHandler();
	}

	@Bean
	public MailUtil mailUtil(JavaMailSender javaMailSender, TemplateEngine templateEngine, MailProperties mailProperties){
		return new MailUtil(javaMailSender, templateEngine, mailProperties);
	}

	@Bean
	public RedisUtil redisUtil(StringRedisTemplate stringRedisTemplate){
		return new RedisUtil(stringRedisTemplate);
	}
}
