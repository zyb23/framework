package me.zyb.framework.wechat.dict;

/**
 * 微信参数
 * @author zhangyingbin
 */
public class WechatParam {
	/** 随机字符串 */
	public static final String ECHOSTR = "echostr";
	/** 随机数 */
	public static final String NONCE = "nonce";
	/** 签名 */
	public static final String SIGNATURE = "signature";
	/** 时间戳 */
	public static final String TIMESTAMP = "timestamp";

	/**
	 * 微信消息参数，XML格式报文
	 */
	public class XmlMsg {
		/** 文本消息内容 */
		public static final String CONTENT = "Content";
		/** 消息创建时间 （整型） */
		public static final String CREATE_TIME = "CreateTime";
		/** 消息描述 */
		public static final String DESCRIPTION = "Description";
		/** 事件类型 */
		public static final String EVENT = "Event";
		/** 事件KEY值，qrscene_为前缀，后面为二维码的参数值 */
		public static final String EVENT_KEY = "EventKey";
		/** 语音格式：amr */
		public static final String FORMAT = "Format";
		/**
		 * <pre>
		 *     发送方账号
		 *     发送方为用户时：openId
		 *     发送方为公众号时：公众号的微信号
		 * </pre>
		 */
		public static final String FROM_USER_NAME = "FromUserName";
		/** 地理位置信息 */
		public static final String LABEL = "Label";
		/** 地理位置纬度 */
		public static final String LATITUDE = "Latitude";
		/** 地理位置纬度 */
		public static final String LOCATION_X = "Location_X";
		/** 地理位置经度 */
		public static final String LOCATION_Y = "Location_Y";
		/** 地理位置经度 */
		public static final String LONGITUDE = "Longitude";
		/**
		 * <pre>
		 *     消息媒体id，可以调用获取临时素材接口拉取数据。
		 *     适用MsgType：image、voice、video、shortvideo
		 * </pre>
		 */
		public static final String MEDIA_ID = "MediaId";
		/** 消息id，64位整型 */
		public static final String MSG_ID = "MsgId";
		/** 消息类型 */
		public static final String MSG_TYPE = "MsgType";
		/** 图片链接（由系统生成） */
		public static final String PIC_URL = "PicUrl";
		/** 地理位置精度 */
		public static final String PRECISION = "Precision";
		/** 语音识别结果，UTF8编码 */
		public static final String RECOGNITION = "Recognition";
		/** 地图缩放大小 */
		public static final String SCALE = "Scale";
		/** 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。 */
		public static final String THUMB_MEDIA_ID = "ThumbMediaId";
		/** 二维码的ticket，可用来换取二维码图片 */
		public static final String ticket = "Ticket";
		/** 消息标题 */
		public static final String TITLE = "Title";
		/**
		 * <pre>
		 *     接收方账号
		 *     接收方为用户时：openId
		 *     接收方为公众号时：公众号的微信号
		 * </pre>
		 */
		public static final String TO_USER_NAME = "ToUserName";
		/** 消息链接 */
		public static final String URL = "Url";
	}
}
