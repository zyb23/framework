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
 * 用户实体-数据表
 * @author zhangyingbin
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_user")
public class SysUser extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 登录名 */
	@Column(name = "login_name", unique = true, nullable = false, updatable = false)
	private String loginName;

	/** 登录密码 */
	@JsonIgnore
	@Column(name = "login_password", nullable = false)
	private String loginPassword;

	/** 密码加盐值 */
	@JsonIgnore
	@Column(name = "salt", nullable = false)
	private String salt;

	/** 用户名 */
	@Column(name = "username")
	private String username;

	/** 头像 */
	@Column(name = "icon")
	private String icon;

	/** 手机号 */
	@Column(name = "mobile")
	private String mobile;

	/** 电子邮箱 */
	@Column(name = "email")
	private String email;

	/** 是否有效 */
	@Column(name = "is_enable")
	private Boolean isEnable = true;

	/** 角色列表 */
	@ToString.Exclude
	@ManyToMany
	@JoinTable(name = "sys_user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<SysRole> roleList = new ArrayList<SysRole>();

	/** 系统默认超级管理员用户，初始化后不允许删除 */
	@Transient
	@JsonIgnore
	public static final Long ADMINISTRATOR_USER_ID = 0L;
}