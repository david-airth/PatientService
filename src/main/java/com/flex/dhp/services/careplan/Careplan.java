package com.flex.dhp.services.careplan;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flex.dhp.services.patient.Patient;

import javax.persistence.*;


/**
 * Created by david.airth on 7/9/17.
 */
@Entity
public class Careplan {
	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String name;

	@JsonIgnore
	@ManyToOne
	private Patient patient;

	public Careplan(Patient patient, String name) {
		this.patient = patient;
		this.name = name;
	}

	Careplan() { // jpa only
	}

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


}


