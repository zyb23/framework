package me.zyb.framework.gecs.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.zyb.framework.core.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 商品规格
 * @author zhangyingbin
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "gecs_spec")
public class GecsSpec extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性名称 */
	@Column(name = "name", unique = true, nullable = false)
	private String name;

	/** 是否有效 */
	@Column(name = "is_enable")
	private Boolean isEnable = true;
}
