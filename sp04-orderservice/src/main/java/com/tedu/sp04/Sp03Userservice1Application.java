package com.tedu.sp04;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;
//@EnableDiscoveryClient
//@SpringBootApplication
//@EnableCircuitBreaker
@EnableFeignClients
@SpringCloudApplication
public class Sp03Userservice1Application {

	public static void main(String[] args) {
		SpringApplication.run(Sp03Userservice1Application.class, args);
	}

}
