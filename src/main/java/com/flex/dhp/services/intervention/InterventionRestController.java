package com.flex.dhp.services.intervention;

import com.flex.dhp.services.AbstractRestController;
import com.flex.dhp.services.careplan.Careplan;
import com.flex.dhp.services.careplan.CareplanNotFoundException;
import com.flex.dhp.services.careplan.CareplanRepository;
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
@RequestMapping("/interventions/{careplanId}")
public class InterventionRestController extends AbstractRestController {

    private final CareplanRepository careplanRepository;
    private final InterventionRepository interventionRepository;

    @Autowired
    InterventionRestController(CareplanRepository careplanRepository, InterventionRepository interventionRepository) {
        this.careplanRepository = careplanRepository;
        this.interventionRepository = interventionRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<Intervention> getInterventions(@PathVariable long careplanId) {

        Assert.isTrue(careplanId > 0, "careplanId is required");

        Careplan careplan = this.validateCareplan(careplanId);

        return this.interventionRepository.findByCareplanId(careplanId);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/{interventionId}")
    Intervention get(@PathVariable long careplanId, @PathVariable Long interventionId) {

        Assert.isTrue(careplanId > 0, "CareplanId is required");
        Assert.isTrue(interventionId > 0, "interventionId is required");

        this.validateCareplan(careplanId);
        return this.validateIntervention(interventionId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{interventionId}")
    ResponseEntity<?> update(@PathVariable Long interventionId, @RequestBody Intervention intervention) {

        Assert.isTrue(interventionId > 0, "interventionId is required");
        Assert.notNull(intervention, "intervention is required");

        Intervention currentIntervention = this.validateIntervention(interventionId);

        //TODO: currently NoOp as nothing can be changed
        //currentIntervention.setName(intervention.getName());

        this.interventionRepository.save(currentIntervention);

        return new ResponseEntity<>(currentIntervention, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> add(@PathVariable long careplanId, @RequestBody Intervention intervention) {

        Assert.isTrue(careplanId > 0, "careplanId is required");
        Assert.notNull(intervention, "intervention is required");

        Careplan careplan = this.validateCareplan(careplanId);

        Intervention newA = new Intervention(careplan, InterventionType.Medication, intervention.getTitle());
        newA.setText(intervention.getTitle());
        newA.setInstructions(intervention.getInstructions());

        Intervention result = interventionRepository.save(newA);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{interventionId}")
    ResponseEntity<?> delete(@PathVariable long interventionId) {

        Assert.isTrue(interventionId > 0, "interventionId is required");

        validateIntervention(interventionId);

        interventionRepository.delete(interventionId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private Careplan validateCareplan(long careplanId) {
        Careplan careplan = this.careplanRepository.findOne(careplanId);
        if (careplan == null)
            throw new CareplanNotFoundException(careplanId);
        else
            return careplan;
    }

    private Intervention validateIntervention(long interventionId) {
        Intervention intervention = this.interventionRepository.findOne(interventionId);
        if (intervention == null)
            throw new InterventionNotFoundException(interventionId);
        else
            return intervention;
    }
}
