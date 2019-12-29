package me.zyb.framework.upms.model;

import lombok.Data;
import me.zyb.framework.upms.dict.PermissionType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 权限模型
 * @author zhangyingbin
 */
@Data
public class UpmsPermissionModel implements Serializable {
	private static final long serialVersionUID = 1L;

	/** ID主键 */
	private Long id;

	/** 权限名称 */
	@NotBlank(message = "权限名称不能为空")
	private String name;

	/** 权限编码 */
	@NotBlank(message = "权限编码不能为空")
	private String code;

	/** 权限类型 */
	@NotNull(message = "权限类型不能为空")
	private PermissionType type;

	/** 顺序（默认升序） */
	private Integer sort = 0;

	/** 操作/页面 */
	private String action;

	/** 路由（前端页面路由） */
	private String route;

	/** 图标 */
	private String icon;

	/** 描述 */
	private String description;

	/** 父级ID */
	private Long parentId;

	/** 上级权限 */
	private UpmsPermissionModel parent;

	/** 下级权限列表 */
	private List<UpmsPermissionModel> children = new ArrayList<UpmsPermissionModel>();

	/** 角色（用户）是否拥有本权限 */
	private Boolean have = false;
}
