package com.flex.dhp.services.assessment;

import com.flex.dhp.services.AbstractActivity;
import com.flex.dhp.services.careplan.Careplan;

import javax.persistence.*;

/**
 * Created by david.airth on 7/13/17.
 */
@Entity
public class Assessment extends AbstractActivity {

    public Assessment(Careplan careplan, String title) {
        super(careplan, title);
    }

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private AssessmentType type;

    public AssessmentType getType() {
        return type;
    }

    Assessment() {
    }// jpa only
}
