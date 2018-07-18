package com.pyy.wechat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@MapperScan(basePackages = "com.pyy.wechat.mapper")
@EnableTransactionManagement
public class WechatAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(WechatAdminApplication.class, args);
	}
}
