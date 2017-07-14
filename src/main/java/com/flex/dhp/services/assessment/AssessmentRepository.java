package com.flex.dhp.services.assessment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

/**
 * Created by david.airth on 7/10/17.
 */
public interface AssessmentRepository extends JpaRepository<Assessment, Long> {
    Collection<Assessment> findByCareplanId(long careplanId);

    void deleteAllByCareplan_Id(long careplanId);

}
