package com.neo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger配置类  
 * .apis swagger扫描该目录下api 
 * 	@Api：修饰整个类，描述Controller的作用
	@ApiOperation：描述一个类的一个方法，或者说一个接口
	@ApiParam：单个参数描述
	@ApiModel：用对象来接收参数
	@ApiProperty：用对象接收参数时，描述对象的一个字段
	@ApiResponse：HTTP响应其中1个描述
	@ApiResponses：HTTP响应整体描述
	@ApiIgnore：使用该注解忽略这个API
	@ApiError ：发生错误返回的信息
	@ApiImplicitParam：一个请求参数
	@ApiImplicitParams：多个请求参数
 * @author pengpeng135
 *
 */

@Configuration
public class Swagger2 {

	@Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.neo"))
                .paths(PathSelectors.any())
                .build();
    }
	
	private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("RESTful APIs")
                .description("")
                .termsOfServiceUrl("")
                .contact(new Contact("", "", ""))
                .version("1.0")
                .build();
    }
	
}
