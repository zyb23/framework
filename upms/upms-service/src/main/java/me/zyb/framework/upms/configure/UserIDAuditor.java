package me.zyb.framework.upms.configure;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * @author zhangyingbin
 */
@Configuration
public class UserIDAuditor implements AuditorAware<Long> {

	@Override
	public Optional<Long> getCurrentAuditor() {
		return Optional.ofNullable(ShiroAuthHelper.getCurrentSysUserId());
	}
}
