package com.flex.dhp.services.careplan;

import com.flex.dhp.services.AbstractRestController;
import com.flex.dhp.services.assessment.AssessmentRepository;
import com.flex.dhp.services.intervention.InterventionRepository;
import com.flex.dhp.services.patient.Patient;
import com.flex.dhp.services.patient.PatientNotFoundException;
import com.flex.dhp.services.patient.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

/**
 * Created by david.airth on 7/11/17.
 */
@RestController
@RequestMapping("/careplans/{patientId}")
public class CareplanRestController extends AbstractRestController {

    private final PatientRepository patientRepository;
    private final CareplanRepository careplanRepository;
    private final AssessmentRepository assessmentRepository;
    private final InterventionRepository interventionRepository;

    @Autowired
    CareplanRestController(PatientRepository patientRepository,
                           CareplanRepository careplanRepository,
                           AssessmentRepository assessmentRepository,
                           InterventionRepository interventionRepository) {

        this.patientRepository = patientRepository;
        this.careplanRepository = careplanRepository;
        this.assessmentRepository = assessmentRepository;
        this.interventionRepository = interventionRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<Careplan> getcareplans(@PathVariable long patientId) {

        Assert.isTrue(patientId > 0, "patientId is required");

        Patient patient = this.validatePatient(patientId);

        return this.careplanRepository.findByPatientId(patientId);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/{careplanId}")
    Careplan get(@PathVariable long patientId, @PathVariable Long careplanId) {

        Assert.isTrue(patientId > 0, "patientId is required");
        Assert.isTrue(careplanId > 0, "careplanId is required");

        this.validatePatient(patientId);
        return this.validateCareplan(careplanId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{careplanId}")
    ResponseEntity<?> update(@PathVariable Long careplanId, @RequestBody Careplan careplan) {

        Assert.isTrue(careplanId > 0, "careplanId is required");
        Assert.notNull(careplan, "careplan is required");

        Careplan currentCareplan = this.validateCareplan(careplanId);

        currentCareplan.setName(careplan.getName());

        this.careplanRepository.save(currentCareplan);

        return new ResponseEntity<Careplan>(currentCareplan, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> add(@PathVariable long patientId, @RequestBody Careplan careplan) {

        Assert.isTrue(patientId > 0, "patientId is required");
        Assert.notNull(careplan, "careplan is required");

        Patient patient = this.validatePatient(patientId);

        Careplan result = careplanRepository.save(new Careplan(patient, careplan.getName()));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @Transactional
    @RequestMapping(method = RequestMethod.DELETE, value = "/{careplanId}")
    ResponseEntity<?> delete(@PathVariable long careplanId) {

        Assert.isTrue(careplanId > 0, "careplanId is required");

        Careplan careplan = validateCareplan(careplanId);

        assessmentRepository.delete(careplan.getAssessments());
        interventionRepository.delete(careplan.getInterventions());
        careplanRepository.delete(careplanId);

        return new ResponseEntity<Careplan>(HttpStatus.NO_CONTENT);
    }

    private Patient validatePatient(long patientId) {
        Patient patient = this.patientRepository.findOne(patientId);
        if (patient == null)
            throw new PatientNotFoundException(patientId);
        else
            return patient;
    }

    private Careplan validateCareplan(long careplanId) {
        Careplan careplan = this.careplanRepository.findOne(careplanId);
        if (careplan == null)
            throw new CareplanNotFoundException(careplanId);
        else
            return careplan;
    }
}
