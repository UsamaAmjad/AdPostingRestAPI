package com.heycar.AdListingProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.heycar.AdListingProject")
public class AdListingProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdListingProjectApplication.class, args);
	}

}
