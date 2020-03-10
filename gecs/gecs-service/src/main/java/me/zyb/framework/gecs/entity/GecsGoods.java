package me.zyb.framework.gecs.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.zyb.framework.core.base.BaseEntity;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 商品表
 * @author zhangyingbin
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "gecs_goods")
public class GecsGoods extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 商品编码 */
	@Column(name = "code", unique = true, nullable = false, updatable = false)
	private String code;

	/** 商品名称 */
	@Column(name = "name", nullable = false)
	private String name;

	/** 缩略图 */
	@Column(name = "thumbnail", nullable = false)
	private String thumbnail;

	/** 商品图片 */
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "gecs_goods_image", joinColumns = {@JoinColumn(name = "goods_id")})
	@Column(name = "url", nullable = false)
	private Set<String> imageSet;

	/** 商品详情 */
	@Column(name = "description")
	private String description;

	/** 商品一级分类 */
	@ManyToOne
	@Column(name = "category_first_id", nullable = false)
	private GecsGoodsCategory categoryFirst;

	/** 商品二级分类 */
	@ManyToOne
	@Column(name = "category_second_id", nullable = false)
	private GecsGoodsCategory categorySecond;

	/** 商品属性列表 */
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "gecs_goods_property_instance", joinColumns = {@JoinColumn(name = "goods_id")})
	@Column(name = "value", nullable = false)
	private Map<GecsGoodsProperty, Set<String>> value = new HashMap<>();
}
