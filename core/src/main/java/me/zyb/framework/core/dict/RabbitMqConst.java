package me.zyb.framework.core.dict;

/**
 * RabbitMQ 常量池
 * @author zhangyingbin
 */
public class RabbitMqConst {
	/** 直连交换机 */
	public static final String EXCHANGE_DIRECT = "exchange.direct";
	/** 分列交换机 */
	public static final String EXCHANGE_FANOUT = "exchange.fanout";
	/** 主题交换机 */
	public static final String EXCHANGE_TOPIC = "exchange.topic";

	/** 直连队列 */
	public static final String QUEUE_DIRECT = "queue.direct";
	/** 分列队列1 */
	public static final String QUEUE_FANOUT_1 = "queue.fanout.1";
	/** 分列队列2 */
	public static final String QUEUE_FANOUT_2 = "queue.fanout.2";
	/** 分列队列3 */
	public static final String QUEUE_FANOUT_3 = "queue.fanout.3";
	/** 主题队列 */
	public static final String QUEUE_TOPIC = "queue.topic";


	/** 路由键-直连模式 */
	public static final String ROUTING_KEY_DIRECT = "routing.key.direct";
	/** 路由键-分列模式 */
	public static final String ROUTING_KEY_FANOUT = "routing.key.fanout";
	/** 路由键-主题模式 */
	public static final String ROUTING_KEY_TOPIC = "routing.key.topic.*";
}
