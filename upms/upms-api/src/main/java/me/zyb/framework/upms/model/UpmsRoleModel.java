package me.zyb.framework.upms.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 角色模型
 * @author zhangyingbin
 */
@Data
public class UpmsRoleModel implements Serializable {
	private static final long serialVersionUID = 1L;

	/** ID主键 */
	private Long id;

	/** 角色编码 */
	@NotBlank(message = "角色编码不能为空")
	private String code;

	/** 角色名称 */
	@NotBlank(message = "角色名称不能为空")
	private String name;

	/** 描述 */
	private String description;

	/** 创建人ID */
	protected Long creatorId;

	/** 创建时间 */
	protected Date createTime;

	/** 修改人ID */
	protected Long editorId;

	/** 修改时间 */
	protected Date editTime;

	/** 权限列表 */
	private List<UpmsPermissionModel> permissionList;

	/** 权限ID列表 */
	private Set<Long> permissionIdSet;

	/** 权限编码列表 */
	private Set<String> permissionCodeSet;

	/** 树形展示所有权限，并标识出角色拥有的权限 */
	private List<UpmsPermissionModel> permissionTree;

}
