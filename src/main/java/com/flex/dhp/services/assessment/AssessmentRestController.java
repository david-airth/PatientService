package com.flex.dhp.services.assessment;

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
@RequestMapping("/assessments/{careplanId}")
public class AssessmentRestController extends AbstractRestController {

    private final CareplanRepository careplanRepository;
    private final AssessmentRepository assessmentRepository;

    @Autowired
    AssessmentRestController(CareplanRepository careplanRepository, AssessmentRepository assessmentRepository) {
        this.careplanRepository = careplanRepository;
        this.assessmentRepository = assessmentRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<Assessment> getAssessments(@PathVariable long careplanId) {

        Assert.isTrue(careplanId > 0, "careplanId is required");

        Careplan careplan = this.validateCareplan(careplanId);

        return this.assessmentRepository.findByCareplanId(careplanId);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/{assessmentId}")
    Assessment get(@PathVariable long careplanId, @PathVariable Long assessmentId) {

        Assert.isTrue(careplanId > 0, "CareplanId is required");
        Assert.isTrue(assessmentId > 0, "assessmentId is required");

        this.validateCareplan(careplanId);
        return this.validateAssessment(assessmentId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{assessmentId}")
    ResponseEntity<?> update(@PathVariable Long assessmentId, @RequestBody Assessment assessment) {

        Assert.isTrue(assessmentId > 0, "assessmentId is required");
        Assert.notNull(assessment, "assessment is required");

        Assessment currentAssessment = this.validateAssessment(assessmentId);

        //TODO: currently NoOp as nothing can be changed
        //currentAssessment.setName(assessment.getName());

        this.assessmentRepository.save(currentAssessment);

        return new ResponseEntity<>(currentAssessment, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> add(@PathVariable long careplanId, @RequestBody Assessment assessment) {

        Assert.isTrue(careplanId > 0, "careplanId is required");
        Assert.notNull(assessment, "assessment is required");

        Careplan careplan = this.validateCareplan(careplanId);

        Assessment newA = new Assessment(careplan, assessment.getTitle());
        newA.setText(assessment.getTitle());
        newA.setInstructions(assessment.getInstructions());

        Assessment result = assessmentRepository.save(newA);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{assessmentId}")
    ResponseEntity<?> delete(@PathVariable long assessmentId) {

        Assert.isTrue(assessmentId > 0, "assessmentId is required");

        validateAssessment(assessmentId);

        assessmentRepository.delete(assessmentId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private Careplan validateCareplan(long careplanId) {
        Careplan careplan = this.careplanRepository.findOne(careplanId);
        if (careplan == null)
            throw new CareplanNotFoundException(careplanId);
        else
            return careplan;
    }

    private Assessment validateAssessment(long assessmentId) {
        Assessment assessment = this.assessmentRepository.findOne(assessmentId);
        if (assessment == null)
            throw new AssessmentNotFoundException(assessmentId);
        else
            return assessment;
    }
}
