package me.zyb.framework.wechat.autoconfigure;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author zhangyingbin
 */
@EntityScan(value = "me.zyb.framework.wechat.entity")
@EnableJpaRepositories(value = {"me.zyb.framework.wechat.repository"})
@ComponentScan(value = {"me.zyb.framework.wechat"})
@Configuration
public class AutoConfiguration {

}
