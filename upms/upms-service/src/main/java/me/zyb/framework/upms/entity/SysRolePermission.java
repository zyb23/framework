package me.zyb.framework.upms.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.zyb.framework.core.base.BaseIdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;
import java.util.Date;

/**
 * 角色权限关系
 * @author zhangyingbin
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_role_permission", uniqueConstraints = {@UniqueConstraint(columnNames = {"role_id", "permission_id"})})
public class SysRolePermission extends BaseIdEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 角色ID */
	@Column(name = "role_id", nullable = false)
	private Long roleId;

	/** 权限ID */
	@Column(name = "permission_id", nullable = false)
	private Long permissionId;

	/** 创建人ID */
	@Column(name = "creator_id", nullable = false, updatable = false)
	private Long creatorId;

	/** 创建时间 */
	@Column(name = "create_time", nullable = false, updatable = false)
	private Date createTime = new Date();
}
