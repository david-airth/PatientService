package com.flex.dhp.patientservice;

/**
 * Created by david.airth on 7/10/17.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/patient/{patientId}")
public class PatientRestController {

	private final PatientRepository patientRepository;

	@Autowired
	PatientRestController(PatientRepository patientRepository) {
		this.patientRepository = patientRepository;
	}

	@RequestMapping(method = RequestMethod.GET)
	Patient getPatient(@PathVariable long patientId) {
		this.validatePatient(patientId);
		return this.patientRepository.findOne(patientId);
	}

	private Patient validatePatient(long patientId) {
		Patient patient = this.patientRepository.findOne(patientId);
		if (patient == null)
			throw new PatientNotFoundException(patientId);
		else
			return patient;
	}
}
