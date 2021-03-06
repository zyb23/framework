package me.zyb.framework.upms.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 用户模型
 * @author zhangyingbin
 */
@Data
public class UpmsUserModel implements Serializable {
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

	/** 头像 */
	private String icon;

	/** 手机号 */
	private String mobile;

	/** 电子邮箱 */
	private String email;

	/** 用户是否有效 */
	private Boolean isEnable;

	/** 创建人ID */
	protected Long creatorId;

	/** 创建时间 */
	protected Date createTime;

	/** 修改人ID */
	protected Long editorId;

	/** 修改时间 */
	protected Date editTime;

	/** 角色名（多个逗号隔开） */
	private String roleNames;

	/** 角色ID列表 */
	private Set<Long> roleIdSet;

	/** 角色编码列表 */
	private Set<String> roleCodeSet;

	/** 角色列表 */
	private List<UpmsRoleModel> roleList;

	/** 权限ID列表 */
	private Set<Long> permissionIdSet;

	/** 权限编码列表 */
	private Set<String> permissionCodeSet;

	/** 权限列表 */
	private List<UpmsPermissionModel> permissionList;

	/** 树形权限列表 */
	private List<UpmsPermissionModel> permissionTree;

	/** 树型目录/导航栏 */
	private List<UpmsPermissionModel> catalogTree;

	/**
	 * 用户菜单树（从顶级菜单开始加载）
	 * 注：如果用户有子级菜单，但没有父级菜单，则不会加载出来
	 */
	private List<UpmsPermissionModel> menuTree;

	/** token（sessionId） */
	private String token;

	/** 部门ID */
	private Long deptId;

	/** 部门对象 */
	private UpmsDeptModel dept;
}
