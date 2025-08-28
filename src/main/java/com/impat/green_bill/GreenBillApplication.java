package com.impat.green_bill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing(auditorAwareRef = "auditorAware")
public class GreenBillApplication {

	public static void main(String[] args) {
		SpringApplication.run(GreenBillApplication.class, args);
	}

}
