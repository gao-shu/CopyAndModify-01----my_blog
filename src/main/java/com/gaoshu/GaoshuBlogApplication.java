package com.gaoshu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.gaoshu.mapper")
public class GaoshuBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(GaoshuBlogApplication.class, args);
	}

}
