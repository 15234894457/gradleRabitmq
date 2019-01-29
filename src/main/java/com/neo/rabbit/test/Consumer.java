package com.neo.rabbit.test;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.rabbitmq.client.BlockedListener;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownListener;
import com.rabbitmq.client.ShutdownSignalException;
import com.rabbitmq.client.impl.AMQCommand;
import com.rabbitmq.client.impl.AMQImpl;

public class Consumer {
	// 交换机命名规范：e_模块_其他
		private static final String EXCHANGE_NAME = "e_tyb_test";

		// 队列命名规范：q_模块_其他
		private static final String QUEUE_NAME = "q_tyb_test";

		// routingkey命名规范：r_模块_其他
		private static final String ROUTING_KEY = "r_tyb_test";

		public static void main(String[] argv) throws Exception {

		    // 注意：factory应为单例，不要每次取消息新建一次对象
		    ConnectionFactory factory = new ConnectionFactory();

		    factory.setHost("127.0.0.1");
		    factory.setPort(5672);// 默认端口
		    factory.setUsername("guest");// 默认用户名
		    factory.setPassword("guest");// 默认密码
		    factory.setVirtualHost("/");// 默认虚拟主机，区分权限

		    // 设置心跳时间，防止长时间未活动被防火墙杀死,默认600秒,单位：秒
//		    factory.setRequestedHeartbeat(60*4); 
		  //连接超时时间,单位：毫秒
//			factory.setConnectionTimeout(1000*2);

			// 注意：connection应为单例，不要每次取消息新建一次对象
			Connection connection = factory.newConnection();

			//监听connection关闭异常
		        connection.addShutdownListener(new ShutdownListener() {
			   @Override
			   public void shutdownCompleted(ShutdownSignalException cause) {
						
			       //connection异常
			       if (cause.isHardError()) {
							
			           System.out.println("connection异常:[" + cause.getMessage() + "]");
							
			           //程序引起的异常，如：connection.close()
			           if (cause.isInitiatedByApplication()) {
								
			               System.out.println("connection关闭异常,重连...begin...");
//				  connection = factory.newConnection();
				  System.out.println("connection关闭异常,重连...end...");
								
			           }
			           else{//rabbitmq服务引起的异常
			   		    AMQCommand amqCommand = (AMQCommand)cause.getReason();
			   	                 if( amqCommand.getMethod() instanceof AMQImpl.Connection.Close ){
			   							    
			                                AMQImpl.Connection.Close close = (AMQImpl.Connection.Close)amqCommand.getMethod();
			   							                     
			                                     if( 320==close.getReplyCode() ){//rabbitmq服务器强制关闭
			   								      
			                                    System.out.println("connection关闭异常,请检查rabbitmq服务器是否正常启动！");
			   		        }
			   		     }
			   	              }
			   	         }
			   				
			   	      }
			   	});
			   		
			           //监听connection阻塞异常
			           connection.addBlockedListener(new BlockedListener() {
			   	    @Override
			   	    public void handleUnblocked() throws IOException {
			   	        System.out.println("connection已解除阻塞！");
			   	    }
			   			
			                @Override
			   	     public void handleBlocked(String reason) throws IOException {
			   	         System.out.println("connection阻塞原因：["+reason+"],请检查内存是否够！");
			   	     }
			   	});
			        // 注意：channel应为单例，不要每次取消息新建一次对象
			       	Channel channel = connection.createChannel();
			       		
			       	//监听channel关闭异常
			       	channel.addShutdownListener(new ShutdownListener() {
			       			
			       	    @Override
			       	    public void shutdownCompleted(ShutdownSignalException cause) {
			       				
			       	        //channel异常
			       	        if (!cause.isHardError()) {
			       	            System.out.println("channel异常:[" + cause.getMessage() + "]");
			       					
			       	        }
			       	    }
			              });

			       	// 同一时间一个消费者只能接收一条消息,web管理界面队列Unacked数值，如多个队列数值累加
			       	channel.basicQos(1);
			        /**
			         * 创建交换机
			         * exchange	交换机名		
			         * type	交换机类型	fanout:广播模式,所有消费者都能收到生产者发送的消息,速度更快,不需设置routingkey
			         * 		             direct:只有与routingkey配置的消费者才能收到消息
			         * 		             topic:与direct相同,只是支持模糊配置,类似正则表达式,功能更强,
			         *                                  *表示通配一个词,#表示通配0个或多个词（注意：是词,不是字母）
			         * 	
			         * durable	durable=true,交换机持久化,rabbitmq服务重启交换机依然存在,保证不丢失;
			         * 		durable=false,相反
			         * 
			         */
			         channel.exchangeDeclare(EXCHANGE_NAME, "direct",true);

			   		
			         /**
			          * 创建队列
			          * queue	队列名
			          * durable	持久化队列，true:服务器重启队列不会丢失;false:反之
			          * exclusive	排他性，true:首次申明的connection连接下可见;
			          * 	        false：所有connection连接下都可见
			          *              connection关闭后队列自动删除,忽略队列持久化
			          * autoDelete	true:无消费者时，队列自动删除；false：无消费者时，队列不会自动删除
			          * arguments		可指定队列里消息总数
			          */
			          Map<String, Object> args = new HashMap<String, Object>();
//			        args.put("x-max-length", 100);	//设置队列里的最大消息数
//			        args.put("x-message-ttl", 1000*10);	//设置消息过期时间,时间一过消息自动删除,单位：毫秒
//			        channel.queueDeclare(QUEUE_NAME, true, false, false, args);
			          channel.queueDeclare(QUEUE_NAME, true, false, false, null);
			        			channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);

			      	System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

			      	// 声明一个消费者
			      	QueueingConsumer consumer = new QueueingConsumer(channel);

			      	    /**
			      	     * queue		队列名
			      	     * autoAck		true:消息从队列删除，不管是否正确处理;false：消息不从队列删除，需要ack响应
			      	     * callback		消费者
			      	     */
			      	   channel.basicConsume(QUEUE_NAME, false, consumer);

			      	       while (true) {

			      	           // 循环获取消息
			      	           QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			      	           String message = new String(delivery.getBody());

			      	           System.out.println(" [x] Received '" + message + "'");
			      	           doWork(message);
			      	           System.out.println(" [x] Done");

			      	           // 确认，消息从队列中删除
			      	           channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);

			      	           // 拒绝，消息不从队列删除
//			      	           channel.basicReject(delivery.getEnvelope().getDeliveryTag(),true);

			      	      }
			      	}

			      	private static void doWork(String task) throws InterruptedException {
			      	     for (char ch : task.toCharArray()) {
			      	         if (ch == ' ') {
			      	             Thread.sleep(1000);
			      	         }
			      	     }
			      	}

			      }

