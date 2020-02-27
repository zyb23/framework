package me.zyb.framework.usoa.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.zyb.framework.core.base.BaseEntity;
import me.zyb.framework.usoa.dict.AddressLabel;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 用户地址表
 * @author zhangyingbin
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "usoa_account_address")
public class UsoaAccountAddress extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 用户账号ID */
	@Column(name = "account_id", nullable = false)
	private String accountId;

	/** 收件人姓名 */
	@Column(name = "consignee_name", nullable = false)
	private String consigneeName;

	/** 收件人手机 */
	@Column(name = "consignee_mobile", nullable = false)
	private String consigneeMobile;

	/** 洲 */
	@Column(name = "continent")
	private String continent;

	/** 国家 */
	@Column(name = "country")
	private String country;

	/** 省 */
	@Column(name = "province", nullable = false)
	private String province;

	/** 市 */
	@Column(name = "city", nullable = false)
	private String city;

	/** 县 */
	@Column(name = "county", nullable = false)
	private String county;

	/** 乡镇 */
	@Column(name = "town")
	private String town;

	/** 村 */
	@Column(name = "village")
	private String village;

	/** 详细地址 */
	@Column(name = "address", nullable = false)
	private String address;

	/** 经度 */
	@Column(name = "longitude")
	private String longitude;

	/** 纬度 */
	@Column(name = "latitude")
	private String latitude;

	/** 标签 */
	@Column(name = "label")
	@Convert(converter = AddressLabel.Converter.class)
	private AddressLabel label;

	/** 是否默认地址（每个用户只能有一个默认地址） */
	@Column(name = "is_default", nullable = false)
	private Boolean isDefault = false;

	/** 是否删除（用户自己删除地址为伪删除） */
	@Column(name = "is_delete", nullable = false)
	private Boolean isDelete = false;
}
