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
@Table(name = "upms_role")
public class UpmsRole extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 角色编码 */
	@Column(name = "code", unique = true, nullable = false, updatable = false)
	private String code;

	/** 角色名称 */
	@Column(name = "name", unique = true, nullable = false)
	private String name;

	/** 描述 */
	@Column(name = "description")
	private String description;

	/** 用户列表 */
	@ToString.Exclude
	@ManyToMany(mappedBy = "roleList")
	private List<UpmsUser> userList = new ArrayList<UpmsUser>();

	/** 权限列表 */
	@ToString.Exclude
	@ManyToMany
	@JoinTable(name = "upms_role_permission", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "permission_id"))
	private List<UpmsPermission> permissionList = new ArrayList<UpmsPermission>();

	/** 系统默认超级管理员角色，初始化后不允许删除 */
	@Transient
	@JsonIgnore
	public static final Long ADMINISTRATOR_ROLE_ID = 0L;
}
