package com.neo.rabbit.topic;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TopicReceiver {

    @RabbitHandler
    @RabbitListener(queues = "topic.message")
    public void process(String message) {
        System.out.println("Topic Receiver1  : " + message);
    }

}
