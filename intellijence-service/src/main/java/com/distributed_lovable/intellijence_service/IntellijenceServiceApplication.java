package com.distributed_lovable.intellijence_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class IntellijenceServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntellijenceServiceApplication.class, args);
	}

}
