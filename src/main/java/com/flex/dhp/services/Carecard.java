package com.flex.dhp.services;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


/**
 * Created by david.airth on 7/9/17.
 */
@Entity
public class Carecard {
    @Id
	@GeneratedValue
	private Long id;

	public String name;

	@JsonIgnore
	@ManyToOne
	private Patient patient;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

    public void setName(String newName) {
        this.name = newName;
    }

	public Patient getPatient() {
		return patient;
	}

	public Carecard(Patient patient, String name) {
		this.patient = patient;
		this.name = name;
	}

	Carecard() { // jpa only
	}
}


