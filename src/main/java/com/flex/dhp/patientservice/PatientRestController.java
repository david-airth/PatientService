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

	private final CarecardRepository carecardRepository;

	@Autowired
	PatientRestController(PatientRepository patientRepository, CarecardRepository carecardRepository) {
		this.patientRepository = patientRepository;
		this.carecardRepository = carecardRepository;
	}

	@RequestMapping(method = RequestMethod.GET)
	Patient getPatient(@PathVariable long patientId) {
		this.validatePatient(patientId);
		return this.patientRepository.findOne(patientId);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/carecards")
	Collection<Carecard> getCarecards(@PathVariable long patientId) {
		this.validatePatient(patientId);
		return this.patientRepository.findOne(patientId).getCarecards();
	}

	@RequestMapping(value = "/carecards", method = RequestMethod.POST)
	ResponseEntity<?> add(@PathVariable long patientId, @RequestBody Carecard carecard) {
		Patient patient = this.validatePatient(patientId);

		Carecard result = carecardRepository.save(new Carecard(patient, carecard.name));

		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(result.getId()).toUri();

		return ResponseEntity.created(location).build();
	}

	private Patient validatePatient(long patientId) {
		Patient patient = this.patientRepository.findOne(patientId);
		if (patient == null)
			throw new PatientNotFoundException(patientId);
		else
			return patient;
	}
}
