package com.flex.dhp.services.intervention;

import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by david.airth on 7/11/17.
 */
@ControllerAdvice
public class InterventionControllerAdvice {
    @ResponseBody
    @ExceptionHandler(InterventionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    VndErrors interventionNotFoundExceptionHandler(InterventionNotFoundException ex) {
        return new VndErrors("error", ex.getMessage());
    }
}
