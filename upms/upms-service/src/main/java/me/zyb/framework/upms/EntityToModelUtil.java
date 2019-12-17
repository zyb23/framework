package me.zyb.framework.upms;

import me.zyb.framework.upms.entity.SysPermission;
import me.zyb.framework.upms.entity.SysRole;
import me.zyb.framework.upms.entity.SysUser;
import me.zyb.framework.upms.model.SysPermissionModel;
import me.zyb.framework.upms.model.SysRoleModel;
import me.zyb.framework.upms.model.SysUserModel;
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
	 * 将SysUser转成SysUserModel
	 * @param entity            用户实体对象
	 * @param needJoinRoleName  是否需要拼接角色名称
	 * @return SysUserModel
	 */
	public static SysUserModel entityToModel(SysUser entity, boolean needJoinRoleName){
		SysUserModel model = new SysUserModel();
		BeanUtils.copyProperties(entity, model, "loginPassword", "roleList");
		if(needJoinRoleName){
			Set<String> roleNames = entity.getRoleList().stream().map(SysRole::getName).collect(Collectors.toSet());
			model.setRoleNames(StringUtils.join(roleNames, ","));
		}
		return model;
	}

	/**
	 * 将SysUser转成SysUserModel
	 * @param entity            用户实体对象
	 * @return SysUserModel
	 */
	public static SysUserModel entityToModel(SysUser entity){
		return entityToModel(entity, false);
	}

	/**
	 * 将SysUser转成SysUserModel
	 * @param entityList        用户实体对象列表
	 * @param needJoinRoleName  是否需要拼接角色名称
	 * @return List<SysUserModel>
	 */
	public static List<SysUserModel> entityToModel(List<SysUser> entityList, boolean needJoinRoleName){
		List<SysUserModel> modelList = new ArrayList<SysUserModel>();
		for(SysUser entity : entityList){
			modelList.add(entityToModel(entity, needJoinRoleName));
		}
		return modelList;
	}

	/**
	 * 将SysUser转成SysUserModel
	 * @param entityList        用户实体对象列表
	 * @return List<SysUserModel>
	 */
	public static List<SysUserModel> entityToModel4User(List<SysUser> entityList){
		return entityToModel(entityList, false);
	}

	/**
	 * 将SysRole转成SysRoleModel
	 * @param entity                    角色实体对象
	 * @param needPermissionList        是否要包含权限列表
	 * @param needPermissionIdList      是否要包含权限ID列表
	 * @param needPermissionCodeList    是事要包含权限编码列表
	 * @return SysRoleModel
	 */
	public static SysRoleModel entityToModel(SysRole entity, boolean needPermissionList, boolean needPermissionIdList, boolean needPermissionCodeList){
		SysRoleModel model = new SysRoleModel();
		BeanUtils.copyProperties(entity, model, "userList", "permissionList");
		List<SysPermission> permissionEntityList = entity.getPermissionList();
		if(needPermissionList){
			model.setPermissionList(entityToModel(permissionEntityList, false, false));
		}
		if(needPermissionIdList){
			List<Long> permissionIdList = permissionEntityList.stream().map(SysPermission::getId).collect(Collectors.toList());
			model.setPermissionIdList(permissionIdList);
		}
		if(needPermissionCodeList){
			List<String> permissionCodeList = permissionEntityList.stream().map(SysPermission::getCode).collect(Collectors.toList());
			model.setPermissionCodeList(permissionCodeList);
		}
		return model;
	}

	/**
	 * 将SysRole转成SysRoleModel
	 * @param entity                    角色实体对象
	 * @return SysRoleModel
	 */
	public static SysRoleModel entityToModel(SysRole entity){
		return entityToModel(entity, false, false, false);
	}

	/**
	 * 将SysRole转成SysRoleModel
	 * @param entityList                角色实体对象列表
	 * @param needPermissionList        是否要包含权限列表
	 * @param needPermissionIdList      是否要包含权限ID列表
	 * @param needPermissionCodeList    是事要包含权限编码列表
	 * @return List<SysRoleModel>
	 */
	public static List<SysRoleModel> entityToModel(List<SysRole> entityList, boolean needPermissionList, boolean needPermissionIdList, boolean needPermissionCodeList){
		List<SysRoleModel> modelList = new ArrayList<SysRoleModel>();
		for(SysRole entity : entityList){
			modelList.add(entityToModel(entity, needPermissionList, needPermissionIdList, needPermissionCodeList));
		}
		return modelList;
	}

	/**
	 * 将SysRole转成SysRoleModel
	 * @param entityList                角色实体对象列表
	 * @return List<SysRoleModel>
	 */
	public static List<SysRoleModel> entityToModel4Role(List<SysRole> entityList){
		return entityToModel(entityList, false, false, false);
	}

	/**
	 * 将SysPermission转成SysPermissionModel
	 * @param entity            权限实体对象
	 * @param needParent        是否要包含父级权限对象
	 * @param needChildren      是事要包含权子级权限列表
	 * @return SysPermissionModel
	 */
	public static SysPermissionModel entityToModel(SysPermission entity, boolean needParent, boolean needChildren){
		SysPermissionModel model = new SysPermissionModel();
		BeanUtils.copyProperties(entity, model, "parent", "children", "roleList");

		SysPermission parentEntity = entity.getParent();
		if(null != parentEntity) {
			//有父级权限时，父级权限ID默认展示
			model.setParentId(parentEntity.getId());
			if(needParent){
				//父级对象只向下，不再向下，不然会死循环
				model.setParent(entityToModel(parentEntity, true, false));
			}
		}
		List<SysPermission> childrenEntity = entity.getChildren();
		if(needChildren && (null != childrenEntity && childrenEntity.size() > 0)){
			List<SysPermissionModel> children = new ArrayList<SysPermissionModel>();
			for (SysPermission child : childrenEntity){
				children.add(entityToModel(child, needParent, true));
			}
			model.setChildren(children);
		}

		return model;
	}

	/**
	 * 将SysPermission转成SysPermissionModel
	 * @param entity            权限实体对象
	 * @return SysPermissionModel
	 */
	public static SysPermissionModel entityToModel(SysPermission entity){
		return entityToModel(entity, false, false);
	}

	/**
	 * 将SysPermission转成SysPermissionModel
	 * @param entityList        权限实体对象列表
	 * @param needParent        是否要包含父级权限对象
	 * @param needChildren      是事要包含权子级权限列表
	 * @return List<SysPermissionModel>
	 */
	public static List<SysPermissionModel> entityToModel(List<SysPermission> entityList, boolean needParent, boolean needChildren){
		List<SysPermissionModel> modelList = new ArrayList<SysPermissionModel>();
		for(SysPermission entity : entityList){
			modelList.add(entityToModel(entity, needParent, needChildren));
		}
		return modelList;
	}

	/**
	 * 将SysPermission转成SysPermissionModel
	 * @param entityList        权限实体对象列表
	 * @return List<SysPermissionModel>
	 */
	public static List<SysPermissionModel> entityToModel4Permission(List<SysPermission> entityList){
		return entityToModel(entityList, false, false);
	}
}
