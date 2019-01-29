package com.neo.rabbit;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author open
 * 队列配置
 *
 */
@Configuration
public class RabbitConfig {

	
    @Bean
    public Queue helloQueue() {
        return new Queue("hello");
    }

    @Bean
    public Queue neoQueue() {
        return new Queue("neo");
    }

    @Bean
    public Queue objectQueue() {
        return new Queue("object");
    }


}
