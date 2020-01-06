package me.zyb.framework.upms.configure;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author zhangyingbin
 */
@Component
public class UserIDAuditor implements AuditorAware<Long> {

	@Override
	public Optional<Long> getCurrentAuditor() {
		return Optional.ofNullable(ShiroAuthHelper.getCurrentUserId());
	}
}
