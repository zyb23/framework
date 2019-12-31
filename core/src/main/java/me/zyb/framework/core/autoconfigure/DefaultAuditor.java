package me.zyb.framework.core.autoconfigure;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * 默认审计类
 * @author zhangyingbin
 */
public class DefaultAuditor implements AuditorAware<Long> {

	@Override
	public Optional<Long> getCurrentAuditor() {
		return Optional.of(-1L);
	}
}
