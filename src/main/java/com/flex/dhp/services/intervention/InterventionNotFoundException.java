package com.flex.dhp.services.intervention;

/**
 * Created by david.airth on 7/11/17.
 */
public class InterventionNotFoundException extends RuntimeException {

    public InterventionNotFoundException(long interventionId) {
        super("Intervention with id '" + interventionId + "' not found");
    }
}
