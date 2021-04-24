package com.xht;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

//@MapperScan("com.xht.mapper")
@EnableScheduling
@SpringBootApplication
public class CrowdsourcingApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrowdsourcingApplication.class, args);
    }

}
