package com.enoch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.enoch"})
@EnableJpaRepositories(basePackages = {"com.enoch"})
public class  RoleApplication {

  public static void main(String... args) {
	  
    SpringApplication.run(RoleApplication.class, args);
  }
}