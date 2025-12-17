package com.abdhu.pemakaian_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PemakaianServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PemakaianServiceApplication.class, args);
	}

}
