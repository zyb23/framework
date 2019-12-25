package me.zyb.framework.wechat.repository;

import me.zyb.framework.wechat.entity.WechatConfig;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhangyingbin
 */
@Repository
@CacheConfig(cacheNames = "WechatConfig")
public interface WechatConfigRepository extends JpaRepository<WechatConfig, Long>, JpaSpecificationExecutor<WechatConfig> {

	/**
	 * 根据appkey查询
	 * @param appKey    应用标识
	 * @return List<WechatConfig>
	 */
	public List<WechatConfig> findByAppKey(String appKey);

	/**
	 * 根据appKey和状态查询
	 * @param appKey    应用Key
	 * @param isEnable  生效状态
	 * @return List<WechatConfig>
	 */
	public List<WechatConfig> findByAppKeyAndIsEnable(String appKey, Boolean isEnable);
}
