package com.flex.dhp.patientservice;

/**
 * Created by david.airth on 7/10/17.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

	private void validatePatient(long patientId) {
		Patient patient = this.patientRepository.findOne(patientId);
		if(patient == null) throw new PatientNotFoundException(patientId);
	}
}
