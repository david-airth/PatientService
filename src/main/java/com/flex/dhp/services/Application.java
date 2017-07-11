package com.flex.dhp.services;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner init(PatientRepository patientRepository,
						   CarecardRepository carecardRepository) {
		return (evt) -> Arrays.asList(
				"Smith,Jones,Adams,Lee,Rho,Fisher,Pollack,Long".split(","))
				.forEach(
						a -> {
							Patient patient = patientRepository.save(new Patient("Joe", a));
							carecardRepository.save(new Carecard(patient,"Care Card 1"));
							carecardRepository.save(new Carecard(patient,"Care Card 2"));
						});
	}
}
