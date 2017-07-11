package com.flex.dhp.patientservice;

/**
 * Created by david.airth on 7/11/17.
 */
public class CarecardNotFoundException extends RuntimeException {

    public CarecardNotFoundException(long carecardId) {
        super("Carecard with id '" + carecardId + "' not found");
    }
}
