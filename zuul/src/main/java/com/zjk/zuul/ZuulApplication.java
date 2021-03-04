package com.zjk.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author : 张江坤
 * @description:TODO
 * @date :2020/3/12 10:30
 */
@EnableZuulProxy
@SpringBootApplication
@ComponentScan(basePackages = "com.zjk.zuul.Filter")
public class ZuulApplication {

    public static void main(String[] args){
        SpringApplication.run(ZuulApplication.class,args);
    }

}
