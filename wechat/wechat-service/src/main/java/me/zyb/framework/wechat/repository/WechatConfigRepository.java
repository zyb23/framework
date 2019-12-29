package me.zyb.framework.wechat.repository;

import me.zyb.framework.wechat.entity.WechatConfig;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author zhangyingbin
 */
@Repository
@CacheConfig(cacheNames = "WechatConfig")
public interface WechatConfigRepository extends JpaRepository<WechatConfig, Long>, JpaSpecificationExecutor<WechatConfig> {

	/**
	 * 根据appkey查询
	 * @param appKey    应用标识
	 * @return WechatConfig
	 */
	public WechatConfig findByAppKey(String appKey);
}
