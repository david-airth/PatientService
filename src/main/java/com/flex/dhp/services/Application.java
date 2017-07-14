package com.flex.dhp.services;

import com.flex.dhp.services.assessment.Assessment;
import com.flex.dhp.services.assessment.AssessmentRepository;
import com.flex.dhp.services.careplan.Careplan;
import com.flex.dhp.services.careplan.CareplanRepository;
import com.flex.dhp.services.patient.Patient;
import com.flex.dhp.services.patient.PatientRepository;
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
						   CareplanRepository careplanRepository,
						   AssessmentRepository assessmentRepository) {
		return (evt) -> Arrays.asList(
				"Joe:Smith,Jan:Jones,Bob:Adams,Janet:Lee,Richard:Rho,Don:Fisher,Shirley:Pollack,Susan:Long".split(","))
				.forEach(
						a -> {
							String name[] = a.split(":");
							Patient patient = patientRepository.save(new Patient(name[0], name[1]));
							Careplan careplan1 = new Careplan(patient, "Care Plan 1");
							Careplan careplan2 = new Careplan(patient, "Care Plan 2");
							careplanRepository.save(careplan1);
							careplanRepository.save(careplan2);

							assessmentRepository.save(new Assessment(careplan1, "Assessment 1"));
							assessmentRepository.save(new Assessment(careplan1, "Assessment 2"));
							assessmentRepository.save(new Assessment(careplan2, "Assessment 1"));
							assessmentRepository.save(new Assessment(careplan2, "Assessment 2"));

						});
	}
}
