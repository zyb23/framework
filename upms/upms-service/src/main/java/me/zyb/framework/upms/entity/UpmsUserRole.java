package me.zyb.framework.upms.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.zyb.framework.core.base.BaseIdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;

/**
 * 用户角色关系
 * @author zhangyingbin
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "upms_user_role", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role_id"})})
public class UpmsUserRole extends BaseIdEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 用户ID */
	@Column(name = "user_id", nullable = false)
	private Long userId;

	/** 角色ID */
	@Column(name = "role_id", nullable = false)
	private Long roleId;
}
