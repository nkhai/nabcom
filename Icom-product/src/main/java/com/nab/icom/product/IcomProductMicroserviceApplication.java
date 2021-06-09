package com.nab.icom.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.nab.*")
public class IcomProductMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(IcomProductMicroserviceApplication.class, args);
	}
}
