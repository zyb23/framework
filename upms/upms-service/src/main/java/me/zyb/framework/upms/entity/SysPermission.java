package me.zyb.framework.upms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import me.zyb.framework.core.base.BaseEntity;
import me.zyb.framework.upms.dict.PermissionType;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 权限实体-数据表
 * @author zhangyingbin
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_permission")
public class SysPermission extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 权限名称 */
	@Column(name = "name", nullable = false)
	private String name;

	/** 权限编码 */
	@Column(name = "code", unique = true, nullable = false, updatable = false)
	private String code;

	/** 权限类型 */
	@Column(name = "type", nullable = false, updatable = false)
	@Convert(converter = PermissionType.Converter.class)
	private PermissionType type;

	/** 操作/页面（后端接口地址） */
	@Column(name = "action")
	private String action;

	/** 路由（前端页面路由） */
	private String route;

	/** 前端图标 */
	@Column(name = "icon")
	private String icon;

	/** 顺序（默认升序） */
	@Column(name = "sort", nullable = false)
	private Integer sort = 1;

	/** 描述 */
	@Column(name = "description")
	private String description;

	/** 上级权限 */
	@ManyToOne
	@JoinColumn(name = "parent_id", updatable = false)
	private SysPermission parent = null;

	/** 下级权限列表 */
	@ToString.Exclude
	@OneToMany(mappedBy = "parent")
	private List<SysPermission> children = new ArrayList<SysPermission>();

	/** 角色列表 */
	@ToString.Exclude
	@ManyToMany(mappedBy = "permissionList")
	private List<SysRole> roleList = new ArrayList<SysRole>();

	/** 顶级权限的父级ID（并无实际数据存在） */
	@Transient
	@JsonIgnore
	public static final Long TOP_PARENT_ID = null;

	public SysPermission(){
		super();
	}

	private SysPermission(Long id){
		super();
		this.id = id;
	}

	public void setParent(Long parentId){
		this.parent = new SysPermission(parentId);
	}
}
