package com.ecommerce.search;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = "com.ecommerce.search.repositories")
public class ProductSearchApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProductSearchApplication.class, args);
	}
}
