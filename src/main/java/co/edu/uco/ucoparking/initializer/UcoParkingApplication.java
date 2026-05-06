package co.edu.uco.ucoparking.initializer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "Infrastructure.persistence.entity")
@EnableJpaRepositories(basePackages = "infrastructure.persistence.sql")

public class UcoParkingApplication {

	public static void main(String[] args) {
		SpringApplication.run(UcoParkingApplication.class, args);
	}

}
