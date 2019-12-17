package me.zyb.framework.upms.dict;

/**
 * <p>UPMS权限编码常量定义</p>
 * <p>对应sys_permission表中的code字段</p>
 * <p>可在Shrio的@RequiresPermissions中使用</p>
 * @author zhangyingbin
 */
public class SysPermissionCode {
	/** 系统管理 */
	public static final String SYSTEM_MANAGEMENT = "system:management";

	/** 权限管理 */
	public static final String PERMISSON_MANAGEMENT = "permission:management";
	/** 权限查询 */
	public static final String PERMISSION_QUERY = "permission:query";
	/** 权限新增 */
	public static final String PERMISSION_ADD = "permission:add";
	/** 权限编辑 */
	public static final String PERMISSION_EDIT = "permission:edit";
	/** 权限删除 */
	public static final String PERMISSION_DELETE = "permission:delete";

	/** 角色管理 */
	public static final String ROLE_MANAGEMENT = "role:management";
	/** 角色查询 */
	public static final String ROLE_QUERY = "role:query";
	/** 角色新增 */
	public static final String ROLE_ADD = "role:add";
	/** 角色编辑 */
	public static final String ROLE_EDIT = "role:edit";
	/** 角色删除 */
	public static final String ROLE_DELETE = "role:delete";
	/** 角色保存权限 */
	public static final String ROLE_SAVE_PERMISSION = "role:savePermission";
	/** 角色删除权限 */
	public static final String ROLE_DELETE_PERMISSION = "role:deletePermission";

	/** 用户管理 */
	public static final String USER_MANAGEMENT = "user:management";
	/** 用户查询 */
	public static final String USER_QUERY = "user:query";
	/** 用户新增 */
	public static final String USER_ADD = "user:add";
	/** 用户编辑 */
	public static final String USER_EDIT = "user:edit";
	/** 用户删除 */
	public static final String USER_DELETE = "user:delete";
	/** 用户登录密码修改 */
	public static final String USER_UPDATE_LOGIN_PASSWORD = "user:updateLoginPassword";
	/** 用户冻结 */
	public static final String USER_FREEZE = "user:freeze";
	/** 用户解冻 */
	public static final String USER_UNFREEZE = "user:unfreeze";
	/** 用户保存角色 */
	public static final String USER_SAVE_ROLE = "user:saveRole";
	/** 用户删除角色 */
	public static final String USER_DELETE_ROLE = "user:deleteRole";
	/** 用户锁定 */
	public static final String USER_LOCK = "user:lock";
	/** 用户解锁 */
	public static final String USER_UNLOCK = "user:unlock";

}
