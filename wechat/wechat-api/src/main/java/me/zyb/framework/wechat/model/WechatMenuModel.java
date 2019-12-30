package me.zyb.framework.wechat.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;
import me.zyb.framework.wechat.dict.WechatMenuLevel;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
public class WechatMenuModel implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 主键ID */
	private Long id;

	/** 菜单的响应动作类型 */
	@NotBlank(message = "菜单类型不能为空")
	private String type;

	/** 菜单标题 */
	@NotBlank(message = "菜单标题不能为空")
	private String name;

	/** 菜单KEY值，用于消息接口推送（click等点击类型必须） */
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

	/** 菜单等级 */
	@NotNull(message = "菜单等级不能为空")
	private WechatMenuLevel level;

	/** 上级菜单ID */
	private Long parentId;

	/** 上级菜单 */
	@ToString.Exclude
	private WechatMenuModel parent;

	/** 下级菜单列表 */
	@JSONField(name = "sub_button")
	private List<WechatMenuModel> children = new ArrayList<WechatMenuModel>();
}
