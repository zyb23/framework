package me.zyb.framework.wechat.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.zyb.framework.wechat.EntityToModelUtil;
import me.zyb.framework.wechat.WechatException;
import me.zyb.framework.wechat.entity.WechatConfig;
import me.zyb.framework.wechat.model.WechatConfigModel;
import me.zyb.framework.wechat.repository.WechatConfigRepository;
import me.zyb.framework.wechat.service.WechatConfigService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author zhangyingbin
 */
@Slf4j
@Service
public class WechatConfigServiceImpl implements WechatConfigService {
	
	@Autowired
	private WechatConfigRepository wechatConfigRepository;

	@Override
	public WechatConfigModel save(WechatConfigModel model) {
		WechatConfig entity = null;
		if(null == model.getId()){
			//新增
			List<WechatConfig> list = wechatConfigRepository.findByAppKeyAndIsEnable(model.getAppKey(), true);
			if(null != list && list.size() > 0){
				throw new WechatException("应用已存在生效的配置");
			}else {
				entity = new WechatConfig();
			}
		}else {
			//修改
			Optional<WechatConfig> optional = wechatConfigRepository.findById(model.getId());
			if(optional.isPresent()){
				entity = optional.get();
			}else {
				throw new WechatException("微信开发者配置不存在");
			}
		}
		entity.setAppId(model.getAppId());
		entity.setAppSecret(model.getAppSecret());
		entity.setUrl(model.getUrl());
		entity.setToken(model.getToken());
		entity.setEncodingAesKey(model.getEncodingAesKey());
		entity.setAppId(model.getAppKey());
		if (null != model.getEncryptMode()){
			entity.setEncryptMode(model.getEncryptMode());
		}
		if (null != model.getIsEnable()){
			entity.setIsEnable(model.getIsEnable());
		}
		wechatConfigRepository.save(entity);

		BeanUtils.copyProperties(entity, model);

		return model;
	}

	@Override
	public WechatConfigModel queryById(Long id) {
		Optional<WechatConfig> optional = wechatConfigRepository.findById(id);
		if(optional.isPresent()){
			return EntityToModelUtil.entityToModel(optional.get());
		}
		throw new WechatException("微信开发配置不存在");
	}

	@Override
	public List<WechatConfigModel> queryList(String appKey, Boolean isEnable) {
		List<WechatConfig> entityList = wechatConfigRepository.findByAppKeyAndIsEnable(appKey, isEnable);
		return EntityToModelUtil.entityToModel(entityList);
	}

	@Override
	public List<WechatConfigModel> queryList(String appKey) {
		List<WechatConfig> entityList = wechatConfigRepository.findByAppKey(appKey);
		return EntityToModelUtil.entityToModel(entityList);
	}

	@Override
	public void delete(Long id) {
		wechatConfigRepository.deleteById(id);
	}

	@Override
	public WechatConfigModel updateAccessToken(Long id, String accessToken) {
		Optional<WechatConfig> optional = wechatConfigRepository.findById(id);
		if (optional.isPresent()){
			WechatConfig entity = optional.get();
			entity.setAccessToken(accessToken);
			wechatConfigRepository.save(entity);
			return EntityToModelUtil.entityToModel(entity);
		}
		throw new WechatException("微信开发者配置不存在");
	}
	
}
