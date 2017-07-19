package com.flex.dhp.services;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.flex.dhp.services.careplan.Careplan;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;


/**
 * Created by david.airth on 7/13/17.
 */
@MappedSuperclass
public class AbstractActivity extends AbstractEntity {

    @Column(name = "title")
    private String title;
    @Column(name = "text")
    private String text;
    @Column(name = "instructions")
    private String instructions;
    @Column(name = "careplan_id", updatable = false, insertable = false)
    private long planId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public long getPlanId() {
        return planId;
    }
    //public void setPlanid(long planId) { this.planId = planId;}

    public AbstractActivity(Careplan careplan, String title) {
        Assert.notNull(careplan, "Careplan is required");
        Assert.notNull(title, "Title is required.");

        this.planId = careplan.getId();
        this.careplan = careplan;
        this.title = title;
    }

    @ManyToOne
    @JsonBackReference
    private Careplan careplan;

    public Careplan getCareplan() {
        return careplan;
    }

    protected AbstractActivity() {
    }// jpa only
}