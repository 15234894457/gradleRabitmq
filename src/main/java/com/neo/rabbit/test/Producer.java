package com.neo.rabbit.test;

import java.io.IOException;

import com.rabbitmq.client.BlockedListener;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.client.ShutdownListener;
import com.rabbitmq.client.ShutdownSignalException;
import com.rabbitmq.client.impl.AMQCommand;
import com.rabbitmq.client.impl.AMQImpl;

public class Producer {
	//交换机命名规范：e_模块_其他
		private static final String EXCHANGE_NAME = "e_tyb_test";
		
		//routingkey命名规范：r_模块_其他
		private static final String ROUTING_KEY = "r_tyb_test";
		
		public static void main(String[] argv) throws Exception {
			
		//注意：factory应为单例，不要每次取消息新建一次对象
		ConnectionFactory factory = new ConnectionFactory();

		factory.setHost("127.0.0.1");
		factory.setPort(5672);// 默认端口
		factory.setUsername("guest");// 默认用户名
		factory.setPassword("guest");// 默认密码
		factory.setVirtualHost("/");// 默认虚拟主机，区分权限
			
		// 设置心跳时间，防止长时间未活动被防火墙杀死,默认600秒,单位：秒
//		factory.setRequestedHeartbeat(60*4); 
		//连接超时时间,单位：毫秒
//		factory.setConnectionTimeout(1000*2);

		//注意：connection应为单例，不要每次取消息新建一次对象
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
//		               connection = factory.newConnection();
		               System.out.println("connection关闭异常,重连...end...");
							
		          }else{//rabbitmq服务引起的异常
							
		               AMQCommand amqCommand = (AMQCommand)cause.getReason();
		               if( amqCommand.getMethod() instanceof AMQImpl.Connection.Close ){
		                   AMQImpl.Connection.Close close =  (AMQImpl.Connection.Close)amqCommand.getMethod();
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
	            
	            //注意：channel应为单例，不要每次取消息新建一次对象
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
	    		

	        	/**
	        	 * 创建交换机
	        	 * exchange		交换机名		
	        	 * type		交换机类型	fanout:广播模式,所有消费者都能收到生产者发送的消息,速度更快,不需设置routingkey
	        	 * 		                 direct:只有与routingkey配置的消费者才能收到消息
	        	 * 		                 topic:与direct相同,只是支持模糊配置,类似正则表达式,功能更强,
	        	 *                                                *表示通配一个词,#表示通配0个或多个词（注意：是词,不是字母）
	        	 * 	
	        	 * durable		durable=true,交换机持久化,rabbitmq服务重启交换机依然存在,保证不丢失;
	        	 * 		durable=false,相反
	        	 * 
	        	 */
	        	channel.exchangeDeclare(EXCHANGE_NAME, "direct",true);
	        		
	        	//模拟发送消息
	        	for (int i = 0; i < 100; i++) {

	        	    String message = "Hello World!";
	        	    message += i;
	        			
	        	    /**
	                       * exchange		交换机名, ""为默认交换机，direct类型
	        		     * routingKey	exchange为direct、topic类型时指定routingKey,exchange为fanout类型时指定queueName队列名
	        	     * props	                 MessageProperties.PERSISTENT_TEXT_PLAIN:消息持久化,rabbitmq服务重启消息不会丢失;null:非持久化
	        	     * body		发送消息
	        	     */
	        	   channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
	        	       System.out.println(" [x] Sent '" + message + "'");
	        	   }
	        		
	        	   //关闭连接
//	        	   channel.close();
//	        	   connection.close();
	        	}

	        }

