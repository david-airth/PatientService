package com.flex.dhp.services.assessment;

import com.flex.dhp.services.AbstractRestController;
import com.flex.dhp.services.EntityNotFoundException;
import com.flex.dhp.services.careplan.Careplan;
import com.flex.dhp.services.careplan.CareplanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.Collection;

/**
 * Created by david.airth on 7/11/17.
 */
@RestController
@RequestMapping("/patients/{patientId}/assessments")
public class AssessmentRestController extends AbstractRestController<Assessment> {

    private final CareplanRepository careplanRepository;
    private final AssessmentRepository assessmentRepository;

    @Autowired
    AssessmentRestController(CareplanRepository careplanRepository, AssessmentRepository assessmentRepository) {
        this.careplanRepository = careplanRepository;
        this.assessmentRepository = assessmentRepository;
    }

    @Override
    protected Assessment doGet(Long patientId, long assessmentId) {
        return this.validateAssessment(assessmentId);
    }

    @Override
    protected Collection<Assessment> doGetList(Long patientId) {
        Assert.notNull(patientId, "patientId is required");

        return assessmentRepository.findByPatientId(patientId.longValue());
    }

    @Override
    protected Assessment doCreate(Long patientId, Assessment assessment) {
        Assert.notNull(patientId, "PatientId is required");
        Assert.notNull(assessment, "Assessment is required");

        Careplan careplan = this.validateCareplan(assessment.getPlanId());

        Assessment newA = new Assessment(careplan, assessment.getTitle());
        newA.setText(assessment.getTitle());
        newA.setInstructions(assessment.getInstructions());

        Assessment result = assessmentRepository.save(newA);

        return result;
    }

    @Override
    protected Assessment doUpdate(Long patientId, Assessment assessment) {
        Assert.notNull(patientId, "PatientId is required");
        Assessment currentAssessment = this.validateAssessment(assessment.getId());

        //TODO: currently NoOp as nothing can be changed
        //currentAssessment.setName(assessment.getName());

        return this.assessmentRepository.save(currentAssessment);
    }

    @Transactional
    @Override
    protected void doDelete(Long patientId, long id) {

        Assert.notNull(patientId, "patientId is required");

        validateAssessment(id);

        assessmentRepository.delete(id);

    }

    private Careplan validateCareplan(long careplanId) {
        Careplan careplan = this.careplanRepository.findOne(careplanId);
        if (careplan == null)
            throw new EntityNotFoundException("Careplan", careplanId);
        else
            return careplan;
    }

    private Assessment validateAssessment(long assessmentId) {
        Assessment assessment = this.assessmentRepository.findOne(assessmentId);
        if (assessment == null)
            throw new EntityNotFoundException("Assessment", assessmentId);
        else
            return assessment;
    }
}
