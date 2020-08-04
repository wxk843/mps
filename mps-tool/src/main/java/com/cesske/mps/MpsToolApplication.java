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

    /**
     * 配置上传文件大小的配置
     * @return
     */
//    @Bean
//    public MultipartConfigElement multipartConfigElement() {
//        MultipartConfigFactory factory = new MultipartConfigFactory();
//        //  单个数据大小
//        factory.setMaxFileSize("102400KB");
//        /// 总上传数据大小
//        factory.setMaxRequestSize("102400KB");
//        return factory.createMultipartConfig();
//    }
}
