package me.zyb.framework.upms.condition;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.zyb.framework.core.query.BaseCondition;

/**
 * 角色查询条件
 * @author zhangyingbin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UpmsRoleCondition extends BaseCondition {
	/** 是否要包含权限列表 */
	private Boolean needPermissionList = false;
	/** 是否要包含权限ID列表 */
	private Boolean needPermissionIdSet = false;
	/** 是事要包含权限编码列表 */
	private Boolean needPermissionCodeSet = false;
}
