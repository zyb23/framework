package me.zyb.framework.upms.autoconfigure;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author zhangyingbin
 */
@EntityScan(value = "com.cxkj.framework.upms.entity")
@EnableJpaRepositories(value = {"me.zyb.framework.upms.repository"})
@ComponentScan(value = {"me.zyb.framework.upms"})
@Configuration
public class AutoConfiguration {

}
