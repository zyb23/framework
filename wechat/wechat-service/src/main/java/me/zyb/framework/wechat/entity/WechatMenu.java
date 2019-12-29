package me.zyb.framework.wechat.entity;

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
 * <pre>
 *     微信自定义菜单（按钮）
 *     一级菜单最多3个
 *     二级菜单每组最多5个
 * </pre>
 * @author zhangyingbin
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "wechat_menu")
public class WechatMenu extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 菜单的响应动作类型 */
	@Column(name = "type", nullable = false)
	private String type;

	/** 菜单标题 */
	@Column(name = "name", nullable = false)
	private String name;

	/** 菜单KEY值，用于消息接口推送（click等点击类型必须） */
	@Column(name = "key")
	private String key;

	/**
	 * <pre>
	 *      网页链接（view、miniprogram类型必须）
	 *      用户点击菜单可打开链接，不超过1024字节。
	 *      type为miniprogram时，不支持小程序的老版本客户端将打开本url。
	 * </pre>
	 */
	@Column(name = "url")
	private String url;

	/** 调用新增永久素材接口返回的合法media_id（media_id类型和view_limited类型必须） */
	@Column(name = "media_id")
	private String mediaId;

	/** 小程序的appid，仅认证公众号可配置（miniprogram类型必须） */
	@Column(name = "appid")
	private String appId;

	/** 小程序的页面路径（miniprogram类型必须	） */
	@Column(name = "pagepath")
	private String pagepath;

	/** 应用标识 */
	@Column(name = "app_key", nullable = false)
	private String appKey;

	/** 上级菜单 */
	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "parent_id", updatable = false)
	private WechatMenu parent = null;

	/** 下级菜单列表 */
	@OneToMany(mappedBy = "parent")
	private List<WechatMenu> children = new ArrayList<WechatMenu>();
}
