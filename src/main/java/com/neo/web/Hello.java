package com.neo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neo.rabbit.hello.HelloSender;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@EnableAutoConfiguration
@ComponentScan
@Api("Hello数据接口")
@RequestMapping("/Hello")
public class Hello {

	
	
	@Autowired
	private HelloSender helloSender;
	
	
	@RequestMapping(value="/findAllContract",method=RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "查询",notes="查询")
	public String findAllContract(){	
		helloSender.send();
		return "1";
	}
}
