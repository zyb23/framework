package me.zyb.framework.gecs.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.zyb.framework.core.base.BaseEntity;
import me.zyb.framework.gecs.dict.GoodsPropertyInputMode;
import me.zyb.framework.gecs.dict.GoodsPropertyType;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;

/**
 * 商品属性
 * @author zhangyingbin
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "gecs_goods_property")
public class GecsSpec extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性名称 */
	@Column(name = "name", unique = true, nullable = false)
	private String name;

	/** 是否有效 */
	@Column(name = "is_enable")
	private Boolean isEnable = true;

	/** 商品属性类型 */
	@Column(name = "type", nullable = false, updatable = false)
	@Convert(converter = GoodsPropertyType.Converter.class)
	private GoodsPropertyType type;

	/** 输入方式 */
	@Column(name = "input_mode", nullable = false)
	@Convert(converter = GoodsPropertyInputMode.Converter.class)
	private GoodsPropertyInputMode inputMode;

	/** 属性可选值 */
	private Set<String> valueSet;
}
