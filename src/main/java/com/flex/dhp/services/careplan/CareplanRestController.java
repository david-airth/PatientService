package com.flex.dhp.services.careplan;

import com.flex.dhp.services.patient.*;
import com.flex.dhp.services.BaseRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class CareplanRestController extends BaseRestController {
    private final PatientRepository patientRepository;

    private final CareplanRepository careplanRepository;

    @Autowired
    CareplanRestController(PatientRepository patientRepository, CareplanRepository careplanRepository) {
        this.patientRepository = patientRepository;
        this.careplanRepository = careplanRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<Careplan> getcareplans(@PathVariable long patientId) {

        Assert.isTrue(patientId > 0, "PatientID is required");

        Patient patient = this.validatePatient(patientId);

        return this.careplanRepository.findByPatientId(patientId);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/{careplanId}")
    Careplan get(@PathVariable long patientId, @PathVariable Long careplanId) {

        Assert.isTrue(patientId > 0, "PatientID is required");
        Assert.isTrue(careplanId > 0, "careplanId is required");

        this.validatePatient(patientId);
        return this.validateCareplan(careplanId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{careplanId}")
    ResponseEntity<?> update(@PathVariable Long careplanId, @RequestBody Careplan careplan) {

        Assert.isTrue(careplanId > 0, "careplanId is required");
        Assert.notNull(careplan, "carePlan is required");

        Careplan currentCareplan = this.validateCareplan(careplanId);

        currentCareplan.setName(careplan.getName());

        this.careplanRepository.save(currentCareplan);

        return new ResponseEntity<Careplan>(currentCareplan, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> add(@PathVariable long patientId, @RequestBody Careplan careplan) {

        Assert.isTrue(patientId > 0, "PatientID is required");
        Assert.notNull(careplan, "carePlan is required");

        Patient patient = this.validatePatient(patientId);

        Careplan result = careplanRepository.save(new Careplan(patient, careplan.getName()));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{careplanId}")
    ResponseEntity<?> delete(@PathVariable long careplanId) {

        Assert.isTrue(careplanId > 0, "careplanId is required");

        validateCareplan(careplanId);

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
