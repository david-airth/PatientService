package com.flex.dhp.patientservice;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by david.airth on 7/10/17.
 */
public interface PatientRepository extends JpaRepository<Patient, Long> {

}
