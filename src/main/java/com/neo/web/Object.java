package com.neo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neo.model.User;
import com.neo.rabbit.object.ObjectSender;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@EnableAutoConfiguration
@ComponentScan
@Api("Object数据接口")
@RequestMapping("/Object")
public class Object {

	
	
	@Autowired
	private ObjectSender sender;
	
	@RequestMapping(value="/sendOject",method=RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "查询",notes="查询")
	public String sendOject(){	
		for (int i = 0; i < 100000; i++) {
			User user=new User();
			user.setName("neo"+i);
			user.setPass("123456");
			sender.send(user);
		}
		return "1";
	}
}
