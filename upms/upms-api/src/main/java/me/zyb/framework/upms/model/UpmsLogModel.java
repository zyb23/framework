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

	/** 操作人登录名 */
	private String loginName;

	/** 内容 */
	private String content;

	/** 操作类型 */
	private LogType type;

	/** IP */
	private String ip;

	/** 创建人ID */
	protected Long creatorId;

	/** 创建时间 */
	protected Date createTime;
}
