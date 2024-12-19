package com.ecommerce.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductApplication {

	public static void main(String[] args) {
		System.setProperty("lombok.log.level", "DEBUG");

		SpringApplication.run(ProductApplication.class, args);
	}

}
