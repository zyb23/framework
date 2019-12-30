package me.zyb.framework.wechat.service;

import me.zyb.framework.wechat.model.WechatMenuModel;

import java.util.List;

/**
 * 微信菜单服务
 * @author zhangyingbin
 */
public interface WechatMenuService {
	/**
	 * 新增/修改
	 * @param model 数据模型
	 * @return WechatMenuModel
	 */
	public WechatMenuModel save(WechatMenuModel model);

	/**
	 * 查询树形结构的菜单
	 * @param appKey    应用标识
	 * @return List<WechatMenuModel>
	 */
	public List<WechatMenuModel> queryTree(String appKey);
}
