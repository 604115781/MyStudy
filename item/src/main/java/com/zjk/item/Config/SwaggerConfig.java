package com.zjk.item.Config;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author : 张江坤
 * @description:TODO
 * @date :2020/3/16 20:07
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public Docket createRestApi(){

        //配置swagger 生成API的扫描范围
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.basePackage("com.zjk.item")).paths(
            PathSelectors.any()).build();

    }
    /**
    *@Description: 创建API文档信息
    *@Param: 
    *@return:
    *@Author: 张江坤
    *@date: 2020/3/16 20:13
    */
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder().title("坤哥的专栏").description("SpringCloud学习").termsOfServiceUrl("www.baidu.com").version("1.0").build();
    }

}
