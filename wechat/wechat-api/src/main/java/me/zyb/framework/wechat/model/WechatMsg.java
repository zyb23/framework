package me.zyb.framework.wechat.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * 微信消息体
 * @author zhangyingbin
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "xml")
public class WechatMsg implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 文本消息内容 */
	@XmlElement(name = "Content")
	private String content;
	/** 消息创建时间 （整型） */
	@XmlElement(name = "CreateTime")
	private Long createTime;
	/** 消息描述 */
	@XmlElement(name = "Description")
	private String description;
	/** 事件类型 */
	@XmlElement(name = "Event")
	private String event;
	/** 事件KEY值，qrscene_为前缀，后面为二维码的参数值 */
	@XmlElement(name = "EventKey")
	private String eventKey;
	/** 语音格式：amr */
	@XmlElement(name = "Format")
	private String format;
	/**
	 * <pre>
	 *     发送方账号
	 *     发送方为用户时：openId
	 *     发送方为公众号时：公众号的微信号
	 * </pre>
	 */
	@XmlElement(name = "FromUserName")
	private String fromUserName;
	/** 地理位置信息 */
	@XmlElement(name = "Label")
	private String label;
	/** 地理位置纬度 */
	@XmlElement(name = "Latitude")
	private String latitude;
	/** 地理位置纬度 */
	@XmlElement(name = "Location_X")
	private String locationX;
	/** 地理位置经度 */
	@XmlElement(name = "Location_Y")
	private String locationY;
	/** 地理位置经度 */
	@XmlElement(name = "Longitude")
	private String longitude;
	/**
	 * <pre>
	 *     消息媒体id，可以调用获取临时素材接口拉取数据。
	 *     适用MsgType：image、voice、video、shortvideo
	 * </pre>
	 */
	@XmlElement(name = "MediaId")
	private String mediaId;
	/** 消息id，64位整型 */
	@XmlElement(name = "MsgId")
	private String msgId;
	/** 消息类型 */
	@XmlElement(name = "WechatMsgType")
	private String msgType;
	/** 图片链接（由系统生成） */
	@XmlElement(name = "PicUrl")
	private String picUrl;
	/** 地理位置精度 */
	@XmlElement(name = "Precision")
	private String precision;
	/** 语音识别结果，UTF8编码 */
	@XmlElement(name = "Recognition")
	private String recognition;
	/** 地图缩放大小 */
	@XmlElement(name = "Scale")
	private String scale;
	/** 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。 */
	@XmlElement(name = "ThumbMediaId")
	private String thumbMediaId;
	/** 二维码的ticket，可用来换取二维码图片 */
	@XmlElement(name = "Ticket")
	private String ticket;
	/** 消息标题 */
	@XmlElement(name = "Title")
	private String title;
	/**
	 * <pre>
	 *     接收方账号
	 *     接收方为用户时：openId
	 *     接收方为公众号时：公众号的微信号
	 * </pre>
	 */
	@XmlElement(name = "ToUserName")
	private String toUserName;
	/** 消息链接 */
	@XmlElement(name = "Url")
	private String url;
}
