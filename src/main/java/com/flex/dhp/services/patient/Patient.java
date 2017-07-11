package com.flex.dhp.services.patient;

import com.flex.dhp.services.carecard.Carecard;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by david.airth on 7/9/17.
 */
@Entity
public class Patient {
	@Id
	@GeneratedValue
	private Long id;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @OneToMany(mappedBy = "patient")
    private Set<Carecard> carecards = new HashSet<>();

    public Patient(String firstname, String lastname) {
        this.lastname = lastname;
        this.firstname = firstname;
    }

    Patient() {
    } // jpa only

	public Long getId() {
		return id;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

    public Set<Carecard> getCarecards() {
        return carecards;
    }

}
