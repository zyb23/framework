package me.zyb.framework.upms.condition;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.zyb.framework.core.query.BaseCondition;
import me.zyb.framework.upms.dict.LogType;

/**
 * 日志查询条件
 * @author zhangyingbin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UpmsLogCondition extends BaseCondition {
	/** 操作人登录名 */
	private String loginName;
	/** 操作类型 */
	private LogType type;
}
