package com.flex.dhp.patientservice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.hateoas.VndErrors;

/**
 * Created by david.airth on 7/11/17.
 */
@ControllerAdvice
public class CarecardControllerAdvice {
    @ResponseBody
    @ExceptionHandler(CarecardNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    VndErrors carecardNotFoundExceptionHandler(CarecardNotFoundException ex) {
        return new VndErrors("error", ex.getMessage());
    }
}
