package me.zyb.framework.gecs.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.zyb.framework.core.base.BaseEntity;
import me.zyb.framework.core.convert.StringListConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * 库存量单位（Stock Keeping Unit）指一个具体的商品
 * @author zhangyingbin
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "gecs_sku")
public class GecsSku extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 标准产品 */
	@Column(name = "spu_id", nullable = false, updatable = false)
	private Long spuId;

	/** 商品标题 */
	@Column(name = "title", nullable = false)
	private Long title;

	/** 商品的图片 */
	@Column(name = "image")
	@Convert(converter = StringListConverter.class)
	private Set<String> imageSet;

	/** 销售价格 */
	@Column(name = "price", nullable = false)
	private BigDecimal price;

	/** 特有规格属性在spu属性模板中对应下标组合 */
	@Column(name = "index", nullable = false)
	private String index;

	/** 特有规格参数，json，反序列化时应使用LinkedHashMap，保证有序 */
	@Column(name = "spec", nullable = false)
	private LinkedHashMap spec;

	/** 库存数量 */
	@Column(name = "stock", nullable = false)
	private Long stock;

	/** 是否有效 */
	@Column(name = "is_enable", nullable = false)
	private Boolean isEnable = true;
}
