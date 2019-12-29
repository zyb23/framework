package me.zyb.framework.wechat.repository;

import me.zyb.framework.wechat.entity.WechatMenu;
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
public interface WechatMenuRepository extends JpaRepository<WechatMenu, Long>, JpaSpecificationExecutor<WechatMenu> {

	/**
	 * 根据appkey查询
	 * @param appKey    应用标识
	 * @return List<WechatMenu>
	 */
	public List<WechatMenu> findByAppKey(String appKey);

	/**
	 * 根据appKey和状态查询
	 * @param appKey    应用Key
	 * @param isEnable  生效状态
	 * @return List<WechatConfig>
	 */
	public List<WechatMenu> findByAppKeyAndIsEnable(String appKey, Boolean isEnable);
}
