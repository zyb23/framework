package me.zyb.framework.wechat.dict;

/**
 * 微信事件推送类型
 * @author zhangyingbin
 */
public class WechatEventType {
	/**
	 * <pre>
	 *     自定义菜单事件
	 *     点击菜单拉取消息时的事件推送
	 * </pre>
	 */
	public static final String CLICK = "CLICK";
	/** 上报地理位置事件 */
	public static final String LOCATION = "LOCATION";
	/**
	 * <pre>
	 *     扫描带参数二维码事件
	 *     1、如果用户还未关注公众号，则用户可以关注公众号，关注后微信会将带场景值关注事件推送给开发者
	 *        事件类型：subscribe，事件KEY值：qrscene_为前缀，后面为二维码的参数值
	 *     2、如果用户已经关注公众号，则微信会将带场景值扫描事件推送给开发者
	 *        事件类型，SCAN，事件KEY值：是一个32位无符号整数，即创建二维码时的二维码scene_id
	 * </pre>
	 */
	public static final String SCAN = "SCAN";
	/** 关注事件 */
	public static final String SUBSCRIBE = "subscribe";
	/** 取消关注事件  */
	public static final String UNSUBSCRIBE = "unsubscribe";
	/**
	 * <pre>
	 *     自定义菜单事件
	 *     点击菜单跳转链接时的事件推送
	 * </pre>
	 */
	public static final String VIEW = "VIEW";
}
