package com.flex.dhp.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column
    private String title;
    @Column
    private String text;
    @Column
    private String instructions;

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

    public AbstractActivity(Careplan careplan, String title) {
        Assert.notNull(careplan, "careplan is required");
        Assert.notNull(title, "title is required.");

        this.careplan = careplan;
        this.title = title;
    }

    @JsonIgnore
    @ManyToOne
    private Careplan careplan;

    public Careplan getCareplan() {
        return careplan;
    }

    protected AbstractActivity() {
    }// jpa only
}