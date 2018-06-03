package com.pricer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pricer.service.ProductService;

@SpringBootApplication
public class ProductPricerApplication implements CommandLineRunner {

	@Autowired
	ProductService product;


	public static void main(String[] args) {
		SpringApplication.run(ProductPricerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
 		product.getProducts();
	}
}
