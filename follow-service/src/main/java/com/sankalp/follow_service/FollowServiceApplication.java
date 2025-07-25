package com.sankalp.follow_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FollowServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FollowServiceApplication.class, args);
	}

}
