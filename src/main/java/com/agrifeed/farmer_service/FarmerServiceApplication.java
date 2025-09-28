package com.agrifeed.farmer_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FarmerServiceApplication {

	public static void main(String[] args) {

        SpringApplication.run(FarmerServiceApplication.class, args);

	}

}
