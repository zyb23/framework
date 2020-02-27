package me.zyb.framework.gecs.autoconfigure;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author zhangyingbin
 */
@EntityScan(value = "me.zyb.framework.gecs.entity")
@EnableJpaRepositories(value = {"me.zyb.framework.gecs.repository"})
@ComponentScan(value = {"me.zyb.framework.gecs"})
@Configuration
public class AutoConfiguration {

}
