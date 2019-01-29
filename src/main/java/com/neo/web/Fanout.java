package com.neo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neo.rabbit.fanout.FanoutSender;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@EnableAutoConfiguration
@ComponentScan
@Api("Fanout数据接口")
@RequestMapping("/fanout")
public class Fanout {

	
	@Autowired
	private FanoutSender sender;
	
	@RequestMapping(value="/findAllContract",method=RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "查询",notes="查询")
	public String findAllContract(){	
		sender.send();
		return "1";
	}
	

}
