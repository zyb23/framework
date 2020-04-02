package me.zyb.framework.upms.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.zyb.framework.core.base.BaseIdEntity;
import me.zyb.framework.upms.dict.LogType;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 操作日志
 * @author zhangyingbin
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "upms_log")
public class UpmsLog extends BaseIdEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 操作人登录名 */
	@Column(name = "login_name", nullable = false, updatable = false)
	private String loginName;

	/** 内容 */
	@Column(name = "content", nullable = false)
	private String content;

	/** 操作类型 */
	@Column(name = "type", nullable = false, updatable = false)
	@Convert(converter = LogType.Converter.class)
	private LogType type;

	/** IP */
	@Column(name = "ip")
	private String ip;

	/** 创建人ID */
	@CreatedBy
	@Column(name = "creator_id", nullable = false, updatable = false)
	protected Long creatorId = -1L;

	/** 创建时间 */
	@CreatedDate
	@Column(name = "create_time", nullable = false, updatable = false)
	protected Date createTime = new Date();

	public UpmsLog(){
		super();
	}

	private UpmsLog(Long id){
		super();
		this.id = id;
	}
}
