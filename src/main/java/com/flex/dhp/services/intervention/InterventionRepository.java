package com.flex.dhp.services.intervention;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

/**
 * Created by david.airth on 7/10/17.
 */
public interface InterventionRepository extends JpaRepository<Intervention, Long> {

    @Query("SELECT i FROM Intervention i JOIN i.careplan c WHERE c.patient.id = ?1")
    Collection<Intervention> findByPatientId(long patientId);

    void deleteAllByCareplan_Id(long careplanId);


}
