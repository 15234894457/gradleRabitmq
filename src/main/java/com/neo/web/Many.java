package com.neo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neo.rabbit.many.NeoSender;
import com.neo.rabbit.many.NeoSender2;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;



@Controller
@EnableAutoConfiguration
@ComponentScan
@Api("Many数据接口")
@RequestMapping("/Many")
public class Many {

	
	
	
	@Autowired
	private NeoSender neoSender;

	@Autowired
	private NeoSender2 neoSender2;

	@RequestMapping(value="/oneToMany",method=RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "查询",notes="查询")
	public String oneToMany() throws Exception {
		for (int i=0;i<100000;i++){
			neoSender.send(i);
		}
		return "1";
	}

	@RequestMapping(value="/manyToMany",method=RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "查询",notes="查询")
	public String manyToMany() throws Exception {
		for (int i=0;i<100000;i++){
			neoSender.send(i);
			neoSender2.send(i);
		}
		return "1";
	}
}
