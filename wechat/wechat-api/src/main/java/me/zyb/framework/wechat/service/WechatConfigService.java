package me.zyb.framework.wechat.service;

import me.zyb.framework.wechat.model.WechatConfigModel;

import java.util.List;

public interface WechatConfigService {
	
	/**
	 * 新增/修改
	 * @param model 数据模型
	 * @return WechatConfig
	 */
	public WechatConfigModel save(WechatConfigModel model);
	
	/**
	 * 根据ID查询
	 * @param id    微信配置ID
	 * @return WechatConfig
	 */
	public WechatConfigModel queryById(Long id);

	/**
	 * 查询所有
	 * @param appKey    应用标识
	 * @author zhangyingbin
	 * @return List<WechatConfigModel>
	 */
	public List<WechatConfigModel> queryList(String appKey);
	
	/**
	 * 根据状态查询
	 * @param appKey    应用标识
	 * @param isEnable  有效状态
	 * @return List<WechatConfigModel>
	 */
	public List<WechatConfigModel> queryList(String appKey, Boolean isEnable);

	/**
	 * 查询生效的WechatConfig
	 * @param appKey    应用标识
	 * @return WechatConfigModel
	 */
	public WechatConfigModel queryValid(String appKey);
	
	/**
	 * 根据ID删除
	 * @param id    配置ID
	 * @author zhangyingbin
	 */
	public void delete(Long id);
	
	/**
	 * 更新AccessToken（7200s之内有效）
	 * @author zhangyingbin
	 * @param id            配置ID
	 * @param accessToken   微信获取的access_token
	 * @return WechatConfigModel
	 */
	public WechatConfigModel updateAccessToken(Long id, String accessToken);
}
