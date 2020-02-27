package me.zyb.framework.usoa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.zyb.framework.core.base.BaseEntity;
import me.zyb.framework.usoa.dict.AccountState;
import me.zyb.framework.wechat.model.WechatLoginInfo;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 用户登录账号基本信息表
 * @author zhangyingbin
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "usoa_account")
public class UsoaAccount extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 手机号 */
	@Column(name = "mobile", unique = true, nullable = false)
	private String mobile;

	/** 登录密码 */
	@JsonIgnore
	@Column(name = "login_password", nullable = false)
	private String loginPassword;

	/** 昵称 */
	@Column(name = "nickname")
	private String nickname;

	/** 头像 */
	@Column(name = "icon")
	private String icon;

	/** 状态 */
	@Column(name = "state", nullable = false)
	@Convert(converter = AccountState.Converter.class)
	private AccountState state;

	/** 微信登录信息 */
	@Column(name = "wechat_login_info", columnDefinition = "text")
	@Convert(converter = WechatLoginInfo.Converter.class)
	private WechatLoginInfo wechatLoginInfo;
}
