package com.flex.dhp.services.intervention;

import com.flex.dhp.services.AbstractActivity;
import com.flex.dhp.services.careplan.Careplan;

import javax.persistence.*;

/**
 * Created by david.airth on 7/13/17.
 */
@Entity
public class Intervention extends AbstractActivity {
    public Intervention(Careplan careplan, InterventionType type, String title) {
        super(careplan, title);
        this.type = type;
    }

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private InterventionType type;

    public InterventionType getType() {
        return type;
    }

    @Column(name = "supply")
    private int supply;

    public int getSupply() {
        return supply;
    }

    @Column(name = "dosage")
    private int dosage;

    public int getDosage() {
        return dosage;
    }

    @Column(name = "unit")
    private String unit;

    public String getUnit() {
        return unit;
    }

    @Column(name = "result_resettable")
    private boolean resultResettable;

    public boolean isResultResettable() {
        return resultResettable;
    }

    Intervention() {
    }// jpa only
}
