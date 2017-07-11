package com.flex.dhp.services;

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
@RequestMapping("/carecards/{patientId}")
public class CarecardRestController extends BaseRestController {
    private final PatientRepository patientRepository;

    private final CarecardRepository carecardRepository;

    @Autowired
    CarecardRestController(PatientRepository patientRepository, CarecardRepository carecardRepository) {
        this.patientRepository = patientRepository;
        this.carecardRepository = carecardRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<Carecard> getCarecards(@PathVariable long patientId) {

        Assert.isTrue(patientId > 0, "PatientID is required");

        Patient patient = this.validatePatient(patientId);

        return this.carecardRepository.findByPatientId(patientId);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/{carecardId}")
    Carecard get(@PathVariable long patientId, @PathVariable Long carecardId) {

        Assert.isTrue(patientId > 0, "PatientID is required");
        Assert.isTrue(carecardId > 0, "careCardId is required");

        this.validatePatient(patientId);
        return this.validateCarecard(carecardId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{carecardId}")
    ResponseEntity<?> update(@PathVariable Long carecardId, @RequestBody Carecard carecard) {

        Assert.isTrue(carecardId > 0, "careCardId is required");
        Assert.notNull(carecard, "careCard is required");

        Carecard currentCarecard = this.validateCarecard(carecardId);

        currentCarecard.setName(carecard.name);

        this.carecardRepository.save(currentCarecard);

        return new ResponseEntity<Carecard>(currentCarecard, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> add(@PathVariable long patientId, @RequestBody Carecard carecard) {

        Assert.isTrue(patientId > 0, "PatientID is required");
        Assert.notNull(carecard, "careCard is required");

        Patient patient = this.validatePatient(patientId);

        Carecard result = carecardRepository.save(new Carecard(patient, carecard.name));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{carecardId}")
    ResponseEntity<?> delete(@PathVariable long carecardId) {

        Assert.isTrue(carecardId > 0, "careCardId is required");

        carecardRepository.delete(carecardId);

        return new ResponseEntity<Carecard>(HttpStatus.NO_CONTENT);
    }

    private Patient validatePatient(long patientId) {
        Patient patient = this.patientRepository.findOne(patientId);
        if (patient == null)
            throw new PatientNotFoundException(patientId);
        else
            return patient;
    }

    private Carecard validateCarecard(long carecardId) {
        Carecard carecard = this.carecardRepository.findOne(carecardId);
        if (carecard == null)
            throw new CarecardNotFoundException(carecardId);
        else
            return carecard;
    }
}
