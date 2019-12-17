package me.zyb.framework.core.base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>基础实体类，包含[id、creatorId、createTime、editorId、editTime]</p>
 * <p>若实体继承此类，需写个类实现org.springframework.data.domain.AuditorAware用以支持@CreateBy等注解</p>
 * @author zhangyingbin
 */
@Data
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity extends BaseIdEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 创建人ID */
	@CreatedBy
	@Column(name = "creator_id", nullable = false, updatable = false)
	protected Long creatorId;

	/** 创建时间 */
	@CreatedDate
	@Column(name = "create_time", nullable = false, updatable = false)
	protected Date createTime = new Date();

	/** 修改人ID */
	@LastModifiedBy
	@Column(name = "editor_id", nullable = false)
	protected Long editorId;

	/** 修改时间 */
	@LastModifiedDate
	@Column(name = "edit_time", nullable = false)
	protected Date editTime = new Date();
}
