package com.neo.rabbit.hello;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 * @author open
 *发送者
  rabbitTemplate是springboot 提供的默认实现
 */
@Component
public class HelloSender {

	@Autowired
	private AmqpTemplate rabbitTemplate;

	public void send() {
		for (int i = 0; i < 100000; i++) {
			String context = "hello " + new Date();
			System.out.println("Sender : " + context);
			this.rabbitTemplate.convertAndSend("hello", context);
		}
		
	}

}