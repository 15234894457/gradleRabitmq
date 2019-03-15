//package com.neo.rabbitmq;
//
//import com.neo.model.User;
//import com.neo.rabbit.object.ObjectSender;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class ObjectTest {
//
//	@Autowired
//	private ObjectSender sender;
//
//	@Test
//	public void sendOject() throws Exception {
//		for (int i = 0; i < 100; i++) {
//			User user=new User();
//			user.setName("neo"+i);
//			user.setPass("123456");
//			sender.send(user);
//		}
//		
//	}
//
//}