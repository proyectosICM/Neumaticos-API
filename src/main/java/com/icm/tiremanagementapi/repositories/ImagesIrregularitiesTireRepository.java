package com.icm.tiremanagementapi.repositories;

import com.icm.tiremanagementapi.models.ImagesIrregularitiesTireModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImagesIrregularitiesTireRepository extends JpaRepository<ImagesIrregularitiesTireModel, Long> {
    List<ImagesIrregularitiesTireModel> findByIrregularitiesTireModelId(Long id);

    Optional<ImagesIrregularitiesTireModel> findByImageNameAndIrregularitiesTireModelId(String imageName, Long id);
}