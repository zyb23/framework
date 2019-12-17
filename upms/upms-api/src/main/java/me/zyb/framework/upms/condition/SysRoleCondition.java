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
public class SysRoleCondition extends BaseCondition {
	/** 是否要包含权限列表 */
	private Boolean needPermissionList = false;
	/** 是否要包含权限ID列表 */
	private Boolean needPermissionIdList = false;
	/** 是事要包含权限编码列表 */
	private Boolean needPermissionCodeList = false;
}
