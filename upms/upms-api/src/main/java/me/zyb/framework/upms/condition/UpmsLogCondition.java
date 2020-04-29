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
	/** 操作类型 */
	private LogType type;
	/** 操作描述 */
	private String description;
	/** 操作的实体ID */
	private Long entityId;
	/** 操作的实体名 */
	private String entityName;
	/** 操作人名称 */
	private String creatorName;
}
