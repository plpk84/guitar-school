package com.petproject.guitarschool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GuitarSchoolApplication {
	public static void main(String[] args) {
		SpringApplication.run(GuitarSchoolApplication.class, args);
	}
}
