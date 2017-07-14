package com.flex.dhp.services.assessment;

/**
 * Created by david.airth on 7/11/17.
 */
public class AssessmentNotFoundException extends RuntimeException {

    public AssessmentNotFoundException(long assessmentId) {
        super("Assessment with id '" + assessmentId + "' not found");
    }
}
