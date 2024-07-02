package com.enoch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@ComponentScan(basePackages = {"com.enoch"})
@EnableAspectJAutoProxy
@EnableScheduling
@EnableDiscoveryClient
@EnableFeignClients
@EnableWebMvc
@EnableJpaRepositories(basePackages = {"com.enoch","com.enoch.model"})
public class  EnochApplication {

  public static void main(String... args) {
	  ApplicationContext.initContext("VJ");
    SpringApplication.run(EnochApplication.class, args);
  }
}