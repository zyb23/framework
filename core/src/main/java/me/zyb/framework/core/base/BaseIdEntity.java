package me.zyb.framework.core.base;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Id属性[id]
 * @author zhangyingbin
 */
@Data
@MappedSuperclass
public abstract class BaseIdEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/** ID主键 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	protected Long id;
}
