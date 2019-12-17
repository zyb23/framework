package me.zyb.framework.upms.condition;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.zyb.framework.core.query.BaseCondition;

/**
 * 用户查询条件
 * @author zhangyingbin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysPermissionCondition extends BaseCondition {
	/** 父级权限ID */
	private Long parentId;
	/** 是否要包含父级权限对象 */
	private Boolean needParent = false;
	/** 是事要包含权子级权限列表 */
	private Boolean needChildren = false;
}