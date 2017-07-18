package com.flex.dhp.services.intervention;

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
@RequestMapping("/patients/{patientId}/interventions")
public class InterventionRestController extends AbstractRestController<Intervention> {

    private final CareplanRepository careplanRepository;
    private final InterventionRepository interventionRepository;

    @Autowired
    InterventionRestController(CareplanRepository careplanRepository, InterventionRepository interventionRepository) {
        this.careplanRepository = careplanRepository;
        this.interventionRepository = interventionRepository;
    }

    @Override
    protected Intervention doGet(Long patientId, long id) {
        return this.validateIntervention(id);
    }

    @Override
    protected Collection<Intervention> doGetList(Long patientId) {
        Assert.notNull(patientId, "patientId is required");

        return interventionRepository.findByPatientId(patientId.longValue());
    }

    @Override
    protected Intervention doCreate(Long patientId, Intervention intervention) {

        Assert.notNull(patientId, "PatientId is required");

        Careplan careplan = this.validateCareplan(intervention.getPlanId());

        Intervention newA = new Intervention(careplan, intervention.getType(), intervention.getTitle());
        newA.setText(intervention.getTitle());
        newA.setInstructions(intervention.getInstructions());

        Intervention result = interventionRepository.save(newA);

        return result;
    }

    @Override
    protected Intervention doUpdate(Long patientId, Intervention intervention) {
        Assert.notNull(patientId, "PatientId is required");

        Intervention currentIntervention = this.validateIntervention(intervention.getId());

        currentIntervention.setTitle(intervention.getTitle());
        currentIntervention.setText(intervention.getText());
        currentIntervention.setInstructions(intervention.getInstructions());

        return this.interventionRepository.save(currentIntervention);

    }

    @Transactional
    @Override
    protected void doDelete(Long patientId, long id) {

        Assert.notNull(patientId, "PatientId is required");

        validateIntervention(id);

        interventionRepository.delete(id);

    }

    private Careplan validateCareplan(long careplanId) {
        Careplan careplan = this.careplanRepository.findOne(careplanId);
        if (careplan == null)
            throw new EntityNotFoundException("Careplan", careplanId);
        else
            return careplan;
    }

    private Intervention validateIntervention(long interventionId) {
        Intervention intervention = this.interventionRepository.findOne(interventionId);
        if (intervention == null)
            throw new EntityNotFoundException("Intervention", interventionId);
        else
            return intervention;
    }
}
