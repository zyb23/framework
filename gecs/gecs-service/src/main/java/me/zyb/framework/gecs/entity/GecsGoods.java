package me.zyb.framework.gecs.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.zyb.framework.core.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

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
}
