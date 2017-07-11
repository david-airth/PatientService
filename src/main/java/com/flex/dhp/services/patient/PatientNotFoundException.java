package com.flex.dhp.services.patient;

/**
 * Created by david.airth on 7/10/17.
 */
public class PatientNotFoundException extends RuntimeException {

	public PatientNotFoundException(long patientId) {
		super("could not find patient '" + patientId + "'.");
	}
}
