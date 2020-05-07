package me.zyb.framework.core.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import me.zyb.framework.core.dict.RabbitMqConst;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMq消息配置（消费者单纯使用，可不用添加这个配置）
 * @author zhangyingbin
 */
@Slf4j
@Configuration
public class RabbitMqConfig {
	/**
	 * 使用RabbitTemplate接收/发送消息
	 * @param connectionFactory 连接工厂
	 * @return RabbitTemplate
	 */
	@Bean
	public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {
		connectionFactory.setPublisherReturns(true);
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMandatory(true);
		rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> log.info("消息发送成功：correlationData[{}]，ack[{}]，cause[{}]", correlationData, ack, cause));
		rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> log.info("消息丢失：exchange[{}]，route[{}]，replyCode[{}]，replyText[{}]，message:{}", exchange, routingKey, replyCode, replyText, message));
		return rabbitTemplate;
	}

	/**
	 * 直连交换机
	 * @return DirectExchange
	 */
	@Bean
	public DirectExchange directExchange() {
		return new DirectExchange(RabbitMqConst.EXCHANGE_DIRECT);
	}

	/**
	 * 分列交换机
	 * @return FanoutExchange
	 */
	@Bean
	public FanoutExchange fanoutExchange() {
		return new FanoutExchange(RabbitMqConst.EXCHANGE_FANOUT);
	}

	/**
	 * 主题交换机
	 * @return TopicExchange
	 */
	@Bean
	public TopicExchange topicExchange() {
		return new TopicExchange(RabbitMqConst.EXCHANGE_TOPIC);
	}

	/**
	 * 直连队列
	 * @return Queue
	 */
	@Bean
	public Queue queueDirect() {
		return new Queue(RabbitMqConst.QUEUE_DIRECT);
	}

	/**
	 * 分列队列1
	 * @return Queue
	 */
	@Bean
	public Queue queueFanout1() {
		return new Queue(RabbitMqConst.QUEUE_FANOUT_1);
	}

	/**
	 * 分列队列2
	 * @return Queue
	 */
	@Bean
	public Queue queueFanout2() {
		return new Queue(RabbitMqConst.QUEUE_FANOUT_2);
	}

	/**
	 * 分列队列3
	 * @return Queue
	 */
	@Bean
	public Queue queueFanout3() {
		return new Queue(RabbitMqConst.QUEUE_FANOUT_3);
	}

	/**
	 * 主题队列
	 * @return Queue
	 */
	@Bean
	public Queue queueTopic() {
		return new Queue(RabbitMqConst.QUEUE_TOPIC);
	}

	/**
	 * 直接交换机绑定队列
	 * @return Binding
	 */
	@Bean
	public Binding bindingDirect() {
		return BindingBuilder.bind(queueDirect()).to(directExchange()).with(RabbitMqConst.ROUTING_KEY_DIRECT);
	}

	/**
	 * <pre>
	 *     分列交换机绑定队列1
	 *     分列交换机，路由键无需配置，配置也不起作用
	 * </pre>
	 * @return Binding
	 */
	@Bean
	public Binding bindingFanout1() {
		return BindingBuilder.bind(queueFanout1()).to(fanoutExchange());
	}

	/**
	 * <pre>
	 *     分列交换机绑定队列2
	 *     分列交换机，路由键无需配置，配置也不起作用
	 * </pre>
	 * @return Binding
	 */
	@Bean
	public Binding bindingFanout2() {
		return BindingBuilder.bind(queueFanout2()).to(fanoutExchange());
	}

	/**
	 * <pre>
	 *     分列交换机绑定队列3
	 *     分列交换机，路由键无需配置，配置也不起作用
	 * </pre>
	 * @return Binding
	 */
	@Bean
	public Binding bindingFanout3() {
		return BindingBuilder.bind(queueFanout3()).to(fanoutExchange());
	}

	/**
	 * 主题交换机绑定队列
	 * @return Binding
	 */
	@Bean
	public Binding bindingTopic() {
		return BindingBuilder.bind(queueTopic()).to(topicExchange()).with(RabbitMqConst.ROUTING_KEY_TOPIC);
	}
}
