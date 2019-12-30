package me.zyb.framework.wechat.service.impl;

import me.zyb.framework.wechat.EntityToModelUtil;
import me.zyb.framework.wechat.WechatException;
import me.zyb.framework.wechat.configure.WechatProperties;
import me.zyb.framework.wechat.dict.WechatMenuLevel;
import me.zyb.framework.wechat.entity.WechatMenu;
import me.zyb.framework.wechat.model.WechatMenuModel;
import me.zyb.framework.wechat.repository.WechatMenuRepository;
import me.zyb.framework.wechat.service.WechatMenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

/**
 * @author zhangyingbin
 */
public class WechatMenuServiceImpl implements WechatMenuService {
	@Autowired
	private WechatProperties wechatProperties;
	@Autowired
	private WechatMenuRepository wechatMenuRepository;

	@Override
	public WechatMenuModel save(WechatMenuModel model) {
		WechatMenu entity = null;
		if(null == model.getId()){
			//新增
			WechatMenuLevel level = model.getLevel();
			Long parentId = model.getParentId();
			if(determineAdd(level, parentId)){
				entity = new WechatMenu();
				entity.setLevel(level);
				entity.setParent(null != parentId ? parentId : WechatMenu.TOP_PARENT_ID);
				entity.setAppKey(wechatProperties.getAppKey());
			}else {
				throw new WechatException("菜单不满足新增条件");
			}
		}else {
			//修改
			Optional<WechatMenu> optional = wechatMenuRepository.findById(model.getId());
			if(optional.isPresent()){
				entity = optional.get();
			}else {
				throw new WechatException("菜单不存在");
			}
		}
		entity.setAppId(model.getAppId());
		entity.setKey(model.getKey());
		entity.setMediaId(model.getMediaId());
		entity.setName(model.getName());
		entity.setPagepath(model.getPagepath());
		entity.setType(model.getType());
		entity.setUrl(model.getUrl());

		wechatMenuRepository.save(entity);

		BeanUtils.copyProperties(entity, model);

		return model;
	}

	/**
	 * 判断菜单是否满足新增条件
	 * @param level     菜单等级
	 * @param parentId  上级菜单ID
	 * @return boolean
	 */
	private Boolean determineAdd(WechatMenuLevel level, Long parentId){
		if(WechatMenuLevel.FIRST.equals(level)){
			List<WechatMenu> entityList = wechatMenuRepository.findByAppKeyAndLevel(wechatProperties.getAppKey(), level);
			if(WechatMenuLevel.FIRST_MAX <= entityList.size()){
				throw new WechatException("一级菜单最多3个");
			}
			if(null != parentId){
				throw new WechatException("一级菜单不能有上级菜单");
			}
		}else if(WechatMenuLevel.SECOND.equals(level)) {
			Optional<WechatMenu> parentOptinal = wechatMenuRepository.findById(parentId);
			if (parentOptinal.isPresent()) {
				if (!WechatMenuLevel.FIRST.equals(parentOptinal.get().getLevel())) {
					throw new WechatException("二级菜单必须选择一个一级菜单作为上级菜单");
				}
			} else {
				throw new WechatException("二级菜单必须选择一个一级菜单作为上级菜单");
			}
			List<WechatMenu> entityList = wechatMenuRepository.findBYAppKeyAndParent_Id(wechatProperties.getAppKey(), parentId);
			if (WechatMenuLevel.SECOND_MAX <= entityList.size()) {
				throw new WechatException("二级级菜单每组最多5个");
			}
		}else {
			throw new WechatException("菜单等级错误");
		}
		return true;
	}

	@Override
	public List<WechatMenuModel> queryTree(String appKey) {
		List<WechatMenu> entityList = wechatMenuRepository.findByAppKeyAndLevel(appKey, WechatMenuLevel.FIRST);
		return EntityToModelUtil.entityToModel(entityList, true, true);
	}
}
