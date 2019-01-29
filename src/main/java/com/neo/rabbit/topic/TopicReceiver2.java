package com.neo.rabbit.topic;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TopicReceiver2 {

    @RabbitHandler
    @RabbitListener(queues = "topic.messages")
    public void process(String message) {
        System.out.println("Topic Receiver2  : " + message);
    }

}
