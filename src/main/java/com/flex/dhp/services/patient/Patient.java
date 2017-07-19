package com.flex.dhp.services.patient;

import com.flex.dhp.services.AbstractEntity;
import com.flex.dhp.services.careplan.Careplan;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by david.airth on 7/9/17.
 */
@Entity
public class Patient extends AbstractEntity {
    @Id
	@GeneratedValue
	private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstname;

    @Column(name = "last_name", nullable = false)
    private String lastname;

    @OneToMany(mappedBy = "patient")
    private Set<Careplan> careplans = new HashSet<>();

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

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

	public String getLastname() {
		return lastname;
	}

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }


    public Set<Careplan> getCareplans() {
        return careplans;
    }

}
