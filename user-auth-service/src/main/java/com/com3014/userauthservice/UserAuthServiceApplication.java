package com.com3014.userauthservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class UserAuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserAuthServiceApplication.class, args);
	}

}
