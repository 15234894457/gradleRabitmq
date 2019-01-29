package com.neo.rabbit.fanout;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class FanoutReceiverA {

    @RabbitListener(queues = "fanout.A")
    public void process(String message) {
        System.out.println("fanout Receiver A  : " + message);
    }

}
