package me.zyb.framework.upms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import me.zyb.framework.core.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 部门实体-数据表
 * @author zhangyingbin
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "upms_dept")
public class UpmsDept extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 部门名称 */
	@Column(name = "name", unique = true, nullable = false)
	private String name;

	/** 部门级别 */
	@Column(name = "level", nullable = false)
	private Integer level = 1;

	/** 部门负责人 */
	@Column(name = "principal", nullable = false)
	private String principal;

	/** 上级部门 */
	@ManyToOne
	@JoinColumn(name = "parent_id")
	private UpmsDept parent = null;

	/** 下级部门列表 */
	@ToString.Exclude
	@JsonIgnore
	@OneToMany(mappedBy = "parent")
	private List<UpmsDept> children = new ArrayList<UpmsDept>();

	/** 部门成员列表 */
	@ToString.Exclude
	@JsonIgnore
	@OneToMany(mappedBy = "dept")
	private List<UpmsUser> userList = new ArrayList<UpmsUser>();
}