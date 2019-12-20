package me.zyb.framework.wechat;

import me.zyb.framework.wechat.entity.WechatConfig;
import me.zyb.framework.wechat.model.WechatConfigModel;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangyingbin
 */
public class EntityToModelUtil {
	/**
	 * 将WechatConfig转成WechatConfigModel
	 * @param entity            微信开发配置实体
	 * @return WechatConfigModel
	 */
	public static WechatConfigModel entityToModel(WechatConfig entity){
		WechatConfigModel model = new WechatConfigModel();
		BeanUtils.copyProperties(entity, model);
		return model;
	}

	/**
	 * 将WechatConfig转成WechatConfigModel
	 * @param entityList        微信开发配置实体列表
	 * @return List<WechatConfigModel>
	 */
	public static List<WechatConfigModel> entityToModel(List<WechatConfig> entityList){
		List<WechatConfigModel> modelList = new ArrayList<WechatConfigModel>();
		for(WechatConfig entity : entityList){
			modelList.add(entityToModel(entity));
		}
		return modelList;
	}
}
