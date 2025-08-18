package com.sporty.aviationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class AviationServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(AviationServiceApplication.class, args);
  }
}
