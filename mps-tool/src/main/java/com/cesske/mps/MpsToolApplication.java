package com.cesske.mps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableAsync
@Configuration
public class MpsToolApplication {

    public static void main(String[] args) {
        SpringApplication.run(MpsToolApplication.class, args);
    }

}
