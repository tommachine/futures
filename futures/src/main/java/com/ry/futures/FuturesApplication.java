package com.ry.futures;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ry.futures.mapper")
public class FuturesApplication {

    public static void main(String[] args) {
        SpringApplication.run(FuturesApplication.class, args);
    }
}
