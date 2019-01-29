package com.neo.rabbit.topic;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.Random;

@Component
public class TopicSender {

	@Autowired
	private AmqpTemplate rabbitTemplate;

	
	
	/*
	Topic Exchange

	Topic Exchange 转发消息主要是根据通配符。 在这种交换机下，队列和交换机的绑定会定义一种路由模式，那么，通配符就要在这种路由模式和路由键之间匹配后交换机才能转发消息。
	
	在这种交换机模式下：
	
	路由键必须是一串字符，用句号（.） 隔开，比如说 agreements.us，或者 agreements.eu.stockholm 等。
	路由模式必须包含一个 星号（*），主要用于匹配路由键指定位置的一个单词，比如说，一个路由模式是这样子：agreements..b.*，那么就只能匹配路由键是这样子的：第一个单词是 agreements，第四个单词是 b。 井号（#）就表示相当于一个或者多个单词，例如一个匹配模式是agreements.eu.berlin.#，那么，以agreements.eu.berlin开头的路由键都是可以的。
	具体代码发送的时候还是一样，第一个参数表示交换机，第二个参数表示routing key，第三个参数即消息。如下：
	
	rabbitTemplate.convertAndSend("testTopicExchange","key1.a.c.key2", " this is  RabbitMQ!");
	topic 和 direct 类似, 只是匹配上支持了”模式”, 在”点分”的 routing_key 形式中, 可以使用两个通配符:
	
	*表示一个词.
	#表示零个或多个词.
	 */
	public void send() {
		for (int i = 0; i < 100000; i++) {
			String context = "hi, i am message all";
			System.out.println("Sender : " + context);
			this.rabbitTemplate.convertAndSend("topicExchange", "topic.1", context);
		}
		
	}

	/*
	 * 使用queueMessages同时匹配两个队列，queueMessage只匹配”topic.message”队列
	 */
	public void send1() {
		for (int i = 0; i < 100000; i++) {

		String context = "hi, i am message 1";
		System.out.println("Sender : " + context);
		this.rabbitTemplate.convertAndSend("topicExchange", "topic.message", context);
		}
	}

	public void send2() {
		for (int i = 0; i < 100000; i++) {

		String context = "hi, i am messages 2";
		System.out.println("Sender : " + context);
		this.rabbitTemplate.convertAndSend("topicExchange", "topic.messages", context);
		}
	}
	

}