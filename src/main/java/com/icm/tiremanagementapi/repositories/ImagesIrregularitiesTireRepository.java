package com.icm.tiremanagementapi.repositories;

import com.icm.tiremanagementapi.models.ImagesIrregularitiesTireModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagesIrregularitiesTireRepository extends JpaRepository<ImagesIrregularitiesTireModel, Long> {

}
