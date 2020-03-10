package me.zyb.framework.gecs.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import me.zyb.framework.core.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品分类
 * @author zhangyingbin
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "gecs_goods_category")
public class GecsGoodsCategory extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 分类名称 */
	@Column(name = "name", unique = true, nullable = false)
	private String name;

	/** 是否有效 */
	@Column(name = "is_enable")
	private Boolean isEnable = true;

	/** 上级分类 */
	@ManyToOne
	@JoinColumn(name = "parent_id", updatable = false)
	private GecsGoodsCategory parent = null;

	/** 下级分类列表 */
	@ToString.Exclude
	@OneToMany(mappedBy = "parent")
	private List<GecsGoodsCategory> children = new ArrayList<GecsGoodsCategory>();

}
