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
@Table(name = "upms_permission")
public class UpmsPermission extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 权限编码 */
	@Column(name = "code", unique = true, nullable = false, updatable = false)
	private String code;

	/** 权限名称 */
	@Column(name = "name", nullable = false)
	private String name;

	/** 权限类型 */
	@Column(name = "type", nullable = false, updatable = false)
	@Convert(converter = PermissionType.Converter.class)
	private PermissionType type;

	/** 权限级别 */
	@Column(name = "level", nullable = false)
	private Integer level = 1;

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
	private UpmsPermission parent = null;

	/** 下级权限列表 */
	@ToString.Exclude
	@JsonIgnore
	@OneToMany(mappedBy = "parent")
	private List<UpmsPermission> children = new ArrayList<UpmsPermission>();

	/** 角色列表 */
	@ToString.Exclude
	@JsonIgnore
	@ManyToMany(mappedBy = "permissionList")
	private List<UpmsRole> roleList = new ArrayList<UpmsRole>();

	/** 顶级权限的父级ID（并无实际数据存在） */
	@Transient
	@JsonIgnore
	public static final Long TOP_PARENT_ID = null;

	public UpmsPermission(){
		super();
	}

	private UpmsPermission(Long id){
		super();
		this.id = id;
	}

//	public void setParent(Long parentId){
//		this.parent = new UpmsPermission(parentId);
//	}
}
