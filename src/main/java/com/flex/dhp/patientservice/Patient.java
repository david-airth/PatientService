package com.flex.dhp.patientservice;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by david.airth on 7/9/17.
 */
@Entity
public class Patient {
	@OneToMany(mappedBy = "patient")
	private Set<Carecard> carecards = new HashSet<>();

	@Id
	@GeneratedValue
	private Long id;

	public Set<Carecard> getCarecards() {
		return carecards;
	}

	public Long getId() {
		return id;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public String firstname;
	public String lastname;

	public Patient(String firstname, String lastname) {
		this.lastname = lastname;
		this.firstname = firstname;
	}

	Patient() { // jpa only
	}}
