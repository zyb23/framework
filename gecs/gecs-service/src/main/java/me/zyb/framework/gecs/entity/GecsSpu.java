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
import java.util.List;

/**
 * 标准产品单位（Standard Product Unit）
 * @author zhangyingbin
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "gecs_spu")
public class GecsSpu extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 标题 */
	@Column(name = "title", unique = true, nullable = false, updatable = false)
	private String title;

	/** 子标题 */
	@Column(name = "sub_title", unique = true, nullable = false, updatable = false)
	private String subTitle;

	/** 缩略图 */
	@Column(name = "image", nullable = false)
	private String image;

	/** 一级类目 */
	@Column(name = "category_id_1")
	private Long categoryId1;

	/** 二级类目 */
	@Column(name = "category_id_2")
	private Long categoryId2;

	/** 三级类目 */
	@Column(name = "category_id_3")
	private Long categoryId3;

	/** 品牌 */
	@Column(name = "brand_id")
	private Long brandId;

	/** 是否上架 */
	@Column(name = "is_saleable")
	private Boolean isSaleable = true;

	/** 商品描述信息 */
	@Column(name = "description")
	private String description;

	/** 全部规格参数 */
	@Column(name = "spec")
	@Convert(converter = StringListConverter.class)
	private List<String> spec;

	/** 特有规格参数及可选值信息 */
	@Column(name = "spec_template")
	@Convert(converter = StringListConverter.class)
	private String specTemplate;

	/** 包装清单 */
	@Column(name = "packing_list")
	private String packingList;

	/** 售后服务 */
	@Column(name = "after_service")
	private String afterService;
}
