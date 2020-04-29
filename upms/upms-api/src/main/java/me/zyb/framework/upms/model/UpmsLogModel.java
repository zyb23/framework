package me.zyb.framework.upms.model;

import lombok.Data;
import me.zyb.framework.upms.dict.LogType;

import java.io.Serializable;
import java.util.Date;

/**
 * 操作日志
 * @author zhangyingbin
 */
@Data
public class UpmsLogModel implements Serializable {
	private static final long serialVersionUID = 1L;

	/** ID主键 */
	private Long id;

	/** 内容 */
	private String content;

	/** 操作类型 */
	private LogType type;

	/** 操作描述 */
	private String description;

	/** 操作的实体ID */
	private Long entityId;

	/** 操作的实体名 */
	private String entityName;

	/** IP */
	private String ip;

	/** 创建人ID */
	protected Long creatorId;

	/** 操作人名称 */
	private String creatorName;

	/** 创建时间 */
	protected Date createTime;
}
