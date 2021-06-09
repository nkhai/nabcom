package com.nab.icom.order.history;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.nab.*")
public class IcomOrderHistoryMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(IcomOrderHistoryMicroserviceApplication.class, args);
	}
}
