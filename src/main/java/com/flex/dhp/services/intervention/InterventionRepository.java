package com.flex.dhp.services.intervention;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

/**
 * Created by david.airth on 7/10/17.
 */
public interface InterventionRepository extends JpaRepository<Intervention, Long> {
    Collection<Intervention> findByCareplanId(long careplanId);

    void deleteAllByCareplan_Id(long careplanId);

}
