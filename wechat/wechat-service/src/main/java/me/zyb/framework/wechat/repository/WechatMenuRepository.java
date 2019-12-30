package me.zyb.framework.wechat.repository;

import me.zyb.framework.wechat.dict.WechatMenuLevel;
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
@CacheConfig(cacheNames = "WechatMenu")
public interface WechatMenuRepository extends JpaRepository<WechatMenu, Long>, JpaSpecificationExecutor<WechatMenu> {
	/**
	 * 根据appkey查询
	 * @param appKey    应用标识
	 * @return List<WechatMenu>
	 */
	public List<WechatMenu> findByAppKey(String appKey);

	/**
	 * 根据appKey和菜单等级查询
	 * @param appKey    应用标识
	 * @param level     菜单等级
	 * @return List<WechatMenu>
	 */
	public List<WechatMenu> findByAppKeyAndLevel(String appKey, WechatMenuLevel level);

	/**
	 * 根据appKey和上级菜单ID查询
	 * @param appKey    应用标识
	 * @param parentId  上级菜单ID
	 * @return List<WechatMenu>
	 */
	public List<WechatMenu> findBYAppKeyAndParent_Id(String appKey, Long parentId);
}
