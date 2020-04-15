package me.zyb.framework.upms.condition;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.zyb.framework.core.query.BaseCondition;

/**
 * 部门查询条件
 * @author zhangyingbin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UpmsDeptCondition extends BaseCondition {
	/** 上级权限ID */
	private Long parentId;
	/** 是否要包含上级部门对象 */
	private Boolean needParent = false;
	/** 是事要包含权下级部门列表 */
	private Boolean needChildren = false;

	/** 级别 */
	private Integer level;

	/** 负责人 */
	private String principal;
}
