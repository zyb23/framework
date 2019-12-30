package me.zyb.framework.wechat;

import me.zyb.framework.wechat.entity.WechatConfig;
import me.zyb.framework.wechat.entity.WechatMenu;
import me.zyb.framework.wechat.model.WechatConfigModel;
import me.zyb.framework.wechat.model.WechatMenuModel;
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

	/**
	 * 将WechatMenu转成WechatMenuModel
	 * @param entity            权限实体对象
	 * @param needParent        是否要包含父级权限对象
	 * @param needChildren      是事要包含权子级权限列表
	 * @return WechatMenuModel
	 */
	public static WechatMenuModel entityToModel(WechatMenu entity, boolean needParent, boolean needChildren){
		WechatMenuModel model = new WechatMenuModel();
		BeanUtils.copyProperties(entity, model, "parent", "children");

		WechatMenu parentEntity = entity.getParent();
		if(null != parentEntity) {
			//有父级权限时，父级权限ID默认展示
			model.setParentId(parentEntity.getId());
			if(needParent){
				//父级对象只向下，不再向下，不然会死循环
				model.setParent(entityToModel(parentEntity, true, false));
			}
		}
		List<WechatMenu> childrenEntity = entity.getChildren();
		if(needChildren && (null != childrenEntity && childrenEntity.size() > 0)){
			List<WechatMenuModel> children = new ArrayList<WechatMenuModel>();
			for (WechatMenu child : childrenEntity){
				children.add(entityToModel(child, needParent, true));
			}
			model.setChildren(children);
		}

		return model;
	}

	/**
	 * 将WechatMenu转成WechatMenuModel
	 * @param entity            权限实体对象
	 * @return WechatMenuModel
	 */
	public static WechatMenuModel entityToModel(WechatMenu entity){
		return entityToModel(entity, false, false);
	}

	/**
	 * 将WechatMenu转成WechatMenuModel
	 * @param entityList        权限实体对象列表
	 * @param needParent        是否要包含父级权限对象
	 * @param needChildren      是事要包含权子级权限列表
	 * @return List<WechatMenuModel>
	 */
	public static List<WechatMenuModel> entityToModel(List<WechatMenu> entityList, boolean needParent, boolean needChildren){
		List<WechatMenuModel> modelList = new ArrayList<WechatMenuModel>();
		for(WechatMenu entity : entityList){
			modelList.add(entityToModel(entity, needParent, needChildren));
		}
		return modelList;
	}

	/**
	 * 将WechatMenu转成WechatMenuModel
	 * @param entityList        权限实体对象列表
	 * @return List<WechatMenuModel>
	 */
	public static List<WechatMenuModel> entityToModel4WechatMenu(List<WechatMenu> entityList){
		return entityToModel(entityList, false, false);
	}
}
