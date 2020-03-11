package me.zyb.framework.upms.condition;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.zyb.framework.core.query.BaseCondition;

import java.util.Set;

/**
 * 用户查询条件
 * @author zhangyingbin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UpmsUserCondition extends BaseCondition {
	/** 登录名 */
	private String loginName;

	/** 用户名 */
	private String username;

	/** 手机号 */
	private String mobile;

	/** 电子邮箱 */
	private String email;

	/** 是否禁用 */
	private Boolean isEnable;

	/** 角色id */
	private Set<Long> roleIdSet;
}
