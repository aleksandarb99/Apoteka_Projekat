package com.team11.PharmacyProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableScheduling
@SpringBootApplication
@EnableAsync
@EnableTransactionManagement
public class PharmacyProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(PharmacyProjectApplication.class, args);
	}


}
