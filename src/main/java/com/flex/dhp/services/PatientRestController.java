package com.flex.dhp.services;

/*
 * Created by david.airth on 7/10/17.
 */

import com.flex.dhp.common.AbstractRestController;
import com.flex.dhp.common.EntityNotFoundException;
import com.flex.dhp.common.model.Patient;
import com.flex.dhp.common.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.http.HTTPException;
import java.util.Collection;

@RestController
@RequestMapping("/patients")
public class PatientRestController extends AbstractRestController<Patient> {

	private final PatientRepository patientRepository;

	@Autowired
	PatientRestController(PatientRepository patientRepository) {
		this.patientRepository = patientRepository;
	}

	@Override
	protected Patient doGet(Long patientId, long id) {
		Assert.notNull(id, "PatientId is required");
		return validatePatient(id);
	}

	@Override
	protected Collection<Patient> doGetList(Long patientId) {
		throw new HTTPException(405);
		//return null;
	}

	@Override
	protected Patient doCreate(Long patientId, Patient patient) {
		Patient newPatient = this.patientRepository.save(patient);
		return newPatient;
	}

	@Override
	protected Patient doUpdate(Long patientId, Patient patient) {

		Assert.notNull(patient.getId(), "PatientID is required");

		Patient updatedPatient = validatePatient(patient.getId());

		updatedPatient.setFirstname(patient.getFirstname());
		updatedPatient.setLastname(patient.getLastname());

		return this.patientRepository.save(updatedPatient);
	}

	@Override
	protected void doDelete(Long patientId, long id) {

		throw new HTTPException(405);

	}
	private Patient validatePatient(long patientId) {
		Patient patient = this.patientRepository.findOne(patientId);
		if (patient == null)
			throw new EntityNotFoundException("Patient", patientId);
		else
			return patient;
	}
}
