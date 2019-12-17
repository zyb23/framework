package me.zyb.framework.upms.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 用户模型
 * @author zhangyingbin
 */
@Data
public class SysUserModel implements Serializable {
	private static final long serialVersionUID = 1L;

	/** ID主键 */
	private Long id;

	/** 登录名 */
	@NotBlank(message = "登录名不能为空")
	private String loginName;

	/** 登录密码 */
	private String loginPassword;

	/** 确认登录密码 */
	private String confirmPassword;

	/** 用户名 */
	private String username;

	/** 手机号 */
	private String mobile;

	/** 电子邮箱 */
	private String email;

	/** 用户是否有效 */
	private Boolean isEnable;

	/** 角色名（多个逗号隔开） */
	private String roleNames;

	/** 角色ID列表 */
	private List<Long> roleIdList;

	/** 角色列表 */
	private List<SysRoleModel> roleList;

	/** 权限列表 */
	private List<SysPermissionModel> permissionList;

	/** 权限ID列表 */
	private Set<Long> permissionIdList;

	/** 权限编码列表 */
	private Set<String> permissionCodeList;

	/** 树形权限列表 */
	private List<SysPermissionModel> permissionTree;
}