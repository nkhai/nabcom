package com.nab.icom.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
@EnableDiscoveryClient

public class IcomUserMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(IcomUserMicroserviceApplication.class, args);
	}

}
