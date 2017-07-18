package com.flex.dhp.services.patient;

/*
 * Created by david.airth on 7/10/17.
 */

import com.flex.dhp.services.AbstractRestController;
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
	protected Patient doCreate(Long patientId, Patient entity) {
		throw new HTTPException(405);
	}

	@Override
	protected Patient doUpdate(Long patientId, Patient entity) {
		throw new HTTPException(405);
	}

	@Override
	protected void doDelete(Long patientId, long id) {

		throw new HTTPException(405);

	}
	private Patient validatePatient(long patientId) {
		Patient patient = this.patientRepository.findOne(patientId);
		if (patient == null)
			throw new PatientNotFoundException(patientId);
		else
			return patient;
	}
}
