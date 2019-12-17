package me.zyb.framework.upms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import me.zyb.framework.core.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色实体-数据表
 * @author zhangyingbin
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_role")
public class SysRole extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 角色名称 */
	@Column(name = "name", unique = true, nullable = false)
	private String name;

	/** 描述 */
	@Column(name = "description")
	private String description;

	/** 用户列表 */
	@ToString.Exclude
	@ManyToMany(mappedBy = "roleList")
	private List<SysUser> userList = new ArrayList<SysUser>();

	/** 权限列表 */
	@ToString.Exclude
	@ManyToMany
	@JoinTable(name = "sys_role_permission", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "permission_id"))
	private List<SysPermission> permissionList = new ArrayList<SysPermission>();

	/** 系统默认超级管理员角色，初始化后不允许删除 */
	@Transient
	@JsonIgnore
	public static final Long ADMINISTRATOR_ROLE_ID = 0L;
}
