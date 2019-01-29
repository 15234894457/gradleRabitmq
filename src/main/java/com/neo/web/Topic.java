package com.neo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neo.rabbit.topic.TopicSender;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@EnableAutoConfiguration
@ComponentScan
@Api("Topic数据接口")
@RequestMapping("/Topic")
public class Topic {

	
	
	@Autowired
	private TopicSender sender;
	
	
	@RequestMapping(value="/topic",method=RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "查询",notes="查询")
	public String topic(){	
		sender.send();
		return "1";
	}
	@RequestMapping(value="/topi1",method=RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "查询",notes="查询")
	public String topic1(){	
		sender.send1();
		return "1";
	}
	@RequestMapping(value="/topic2",method=RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "查询",notes="查询")
	public String topic2(){	
		sender.send2();
		return "1";
	}
}
