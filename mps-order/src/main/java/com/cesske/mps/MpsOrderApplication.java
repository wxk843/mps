package com.cesske.mps;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.cesske.mps.mapper")
public class MpsOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(MpsOrderApplication.class, args);
    }

}
