package me.zyb.framework.upms;

import me.zyb.framework.upms.entity.UpmsPermission;
import me.zyb.framework.upms.entity.UpmsRole;
import me.zyb.framework.upms.entity.UpmsUser;
import me.zyb.framework.upms.model.UpmsPermissionModel;
import me.zyb.framework.upms.model.UpmsRoleModel;
import me.zyb.framework.upms.model.UpmsUserModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zhangyingbin
 */
public class EntityToModelUtil {
	/**
	 * 将UpmsUser转成UpmsUserModel
	 * @param entity            用户实体对象
	 * @param needJoinRoleName  是否需要拼接角色名称
	 * @return UpmsUserModel
	 */
	public static UpmsUserModel entityToModel(UpmsUser entity, boolean needJoinRoleName){
		UpmsUserModel model = new UpmsUserModel();
		BeanUtils.copyProperties(entity, model, "loginPassword", "roleList");
		if(needJoinRoleName){
			Set<String> roleNames = entity.getRoleList().stream().map(UpmsRole::getName).collect(Collectors.toSet());
			model.setRoleNames(StringUtils.join(roleNames, ","));
		}
		return model;
	}

	/**
	 * 将UpmsUser转成UpmsUserModel
	 * @param entity            用户实体对象
	 * @return UpmsUserModel
	 */
	public static UpmsUserModel entityToModel(UpmsUser entity){
		return entityToModel(entity, false);
	}

	/**
	 * 将UpmsUser转成UpmsUserModel
	 * @param entityList        用户实体对象列表
	 * @param needJoinRoleName  是否需要拼接角色名称
	 * @return List<UpmsUserModel>
	 */
	public static List<UpmsUserModel> entityToModel(List<UpmsUser> entityList, boolean needJoinRoleName){
		List<UpmsUserModel> modelList = new ArrayList<UpmsUserModel>();
		for(UpmsUser entity : entityList){
			modelList.add(entityToModel(entity, needJoinRoleName));
		}
		return modelList;
	}

	/**
	 * 将UpmsUser转成UpmsUserModel
	 * @param entityList        用户实体对象列表
	 * @return List<UpmsUserModel>
	 */
	public static List<UpmsUserModel> entityToModel4User(List<UpmsUser> entityList){
		return entityToModel(entityList, false);
	}

	/**
	 * 将UpmsRole转成UpmsRoleModel
	 * @param entity                    角色实体对象
	 * @param needPermissionList        是否要包含权限列表
	 * @param needPermissionIdList      是否要包含权限ID列表
	 * @param needPermissionCodeList    是事要包含权限编码列表
	 * @return UpmsRoleModel
	 */
	public static UpmsRoleModel entityToModel(UpmsRole entity, boolean needPermissionList, boolean needPermissionIdList, boolean needPermissionCodeList){
		UpmsRoleModel model = new UpmsRoleModel();
		BeanUtils.copyProperties(entity, model, "userList", "permissionList");
		List<UpmsPermission> permissionEntityList = entity.getPermissionList();
		if(needPermissionList){
			model.setPermissionList(entityToModel(permissionEntityList, false, false));
		}
		if(needPermissionIdList){
			List<Long> permissionIdList = permissionEntityList.stream().map(UpmsPermission::getId).collect(Collectors.toList());
			model.setPermissionIdList(permissionIdList);
		}
		if(needPermissionCodeList){
			List<String> permissionCodeList = permissionEntityList.stream().map(UpmsPermission::getCode).collect(Collectors.toList());
			model.setPermissionCodeList(permissionCodeList);
		}
		return model;
	}

	/**
	 * 将UpmsRole转成UpmsRoleModel
	 * @param entity                    角色实体对象
	 * @return UpmsRoleModel
	 */
	public static UpmsRoleModel entityToModel(UpmsRole entity){
		return entityToModel(entity, false, false, false);
	}

	/**
	 * 将UpmsRole转成UpmsRoleModel
	 * @param entityList                角色实体对象列表
	 * @param needPermissionList        是否要包含权限列表
	 * @param needPermissionIdList      是否要包含权限ID列表
	 * @param needPermissionCodeList    是事要包含权限编码列表
	 * @return List<UpmsRoleModel>
	 */
	public static List<UpmsRoleModel> entityToModel(List<UpmsRole> entityList, boolean needPermissionList, boolean needPermissionIdList, boolean needPermissionCodeList){
		List<UpmsRoleModel> modelList = new ArrayList<UpmsRoleModel>();
		for(UpmsRole entity : entityList){
			modelList.add(entityToModel(entity, needPermissionList, needPermissionIdList, needPermissionCodeList));
		}
		return modelList;
	}

	/**
	 * 将UpmsRole转成UpmsRoleModel
	 * @param entityList                角色实体对象列表
	 * @return List<UpmsRoleModel>
	 */
	public static List<UpmsRoleModel> entityToModel4Role(List<UpmsRole> entityList){
		return entityToModel(entityList, false, false, false);
	}

	/**
	 * 将UpmsPermission转成UpmsPermissionModel
	 * @param entity            权限实体对象
	 * @param needParent        是否要包含父级权限对象
	 * @param needChildren      是事要包含权子级权限列表
	 * @return UpmsPermissionModel
	 */
	public static UpmsPermissionModel entityToModel(UpmsPermission entity, boolean needParent, boolean needChildren){
		UpmsPermissionModel model = new UpmsPermissionModel();
		BeanUtils.copyProperties(entity, model, "parent", "children", "roleList");

		UpmsPermission parentEntity = entity.getParent();
		if(null != parentEntity) {
			//有父级权限时，父级权限ID默认展示
			model.setParentId(parentEntity.getId());
			if(needParent){
				//父级对象只向下，不再向下，不然会死循环
				model.setParent(entityToModel(parentEntity, true, false));
			}
		}
		List<UpmsPermission> childrenEntity = entity.getChildren();
		if(needChildren && (null != childrenEntity && childrenEntity.size() > 0)){
			List<UpmsPermissionModel> children = new ArrayList<UpmsPermissionModel>();
			for (UpmsPermission child : childrenEntity){
				children.add(entityToModel(child, needParent, true));
			}
			model.setChildren(children);
		}

		return model;
	}

	/**
	 * 将UpmsPermission转成UpmsPermissionModel
	 * @param entity            权限实体对象
	 * @return UpmsPermissionModel
	 */
	public static UpmsPermissionModel entityToModel(UpmsPermission entity){
		return entityToModel(entity, false, false);
	}

	/**
	 * 将UpmsPermission转成UpmsPermissionModel
	 * @param entityList        权限实体对象列表
	 * @param needParent        是否要包含父级权限对象
	 * @param needChildren      是事要包含权子级权限列表
	 * @return List<UpmsPermissionModel>
	 */
	public static List<UpmsPermissionModel> entityToModel(List<UpmsPermission> entityList, boolean needParent, boolean needChildren){
		List<UpmsPermissionModel> modelList = new ArrayList<UpmsPermissionModel>();
		for(UpmsPermission entity : entityList){
			modelList.add(entityToModel(entity, needParent, needChildren));
		}
		return modelList;
	}

	/**
	 * 将UpmsPermission转成UpmsPermissionModel
	 * @param entityList        权限实体对象列表
	 * @return List<UpmsPermissionModel>
	 */
	public static List<UpmsPermissionModel> entityToModel4Permission(List<UpmsPermission> entityList){
		return entityToModel(entityList, false, false);
	}
}
