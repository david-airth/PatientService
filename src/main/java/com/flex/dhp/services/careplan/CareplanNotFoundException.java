package com.flex.dhp.services.careplan;

/**
 * Created by david.airth on 7/11/17.
 */
public class CareplanNotFoundException extends RuntimeException {

    public CareplanNotFoundException(long careplanId) {
        super("Careplan with id '" + careplanId + "' not found");
    }
}
