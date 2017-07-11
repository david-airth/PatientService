package com.flex.dhp.services.carecard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flex.dhp.services.patient.Patient;

import javax.persistence.*;


/**
 * Created by david.airth on 7/9/17.
 */
@Entity
public class Carecard {
    @Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String name;

	@JsonIgnore
	@ManyToOne
	private Patient patient;

	public Carecard(Patient patient, String name) {
		this.patient = patient;
		this.name = name;
	}

	Carecard() { // jpa only
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


