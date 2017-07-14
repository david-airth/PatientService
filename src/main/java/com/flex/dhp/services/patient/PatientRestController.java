package com.flex.dhp.services.patient;

/*
 * Created by david.airth on 7/10/17.
 */

import com.flex.dhp.services.AbstractRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patients/{patientId}")
public class PatientRestController extends AbstractRestController {

	private final PatientRepository patientRepository;

	@Autowired
	PatientRestController(PatientRepository patientRepository) {
		this.patientRepository = patientRepository;
	}

	@RequestMapping(method = RequestMethod.GET)
	Patient getPatient(@PathVariable long patientId) {

		Assert.isTrue(patientId > 0, "PatientId is required");

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
