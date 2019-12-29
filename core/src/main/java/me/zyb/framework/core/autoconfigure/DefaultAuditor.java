package me.zyb.framework.core.autoconfigure;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * 默认审计类，使用时，需
 * @author zhangyingbin
 */
public class DefaultAuditor implements AuditorAware<Long> {

	@Override
	public Optional<Long> getCurrentAuditor() {
		return Optional.ofNullable(-1L);
	}
}
