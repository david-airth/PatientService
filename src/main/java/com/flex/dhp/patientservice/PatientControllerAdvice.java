package com.flex.dhp.patientservice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.hateoas.VndErrors;

/**
 * Created by david.airth on 7/10/17.
 */
@ControllerAdvice
class PatientControllerAdvice {

	@ResponseBody
	@ExceptionHandler(PatientNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	VndErrors userNotFoundExceptionHandler(PatientNotFoundException ex) {
		return new VndErrors("error", ex.getMessage());
	}
}