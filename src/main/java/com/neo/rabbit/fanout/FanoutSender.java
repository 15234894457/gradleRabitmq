package com.neo.rabbit.fanout;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FanoutSender {

	@Autowired
	private AmqpTemplate rabbitTemplate;

	public void send() {
		
		for (int i = 0; i < 100000; i++) {
		String context = "hi, fanout msg ";
		System.out.println("Sender : " + context);
		this.rabbitTemplate.convertAndSend("fanoutExchange","", context);
		}
	}
	
    /*@RabbitHandler
    @RabbitListener(queues = "fanout.A")
    public void process(String message) {
        System.out.println("fanout Receiver A  : " + message);
    }*/
}