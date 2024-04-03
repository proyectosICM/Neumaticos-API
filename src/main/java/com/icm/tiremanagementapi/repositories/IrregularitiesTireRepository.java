package com.icm.tiremanagementapi.repositories;

import com.icm.tiremanagementapi.models.IrregularitiesTireModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface IrregularitiesTireRepository extends JpaRepository<IrregularitiesTireModel, Long> {
    Page<IrregularitiesTireModel> findByCompanyId(Long companyId, Pageable pageable);

    List<IrregularitiesTireModel> findByCompanyIdAndVehicleModelId(Long companyId, Long vehicleId);

    Page<IrregularitiesTireModel> findByCompanyIdAndVehicleModelId(Long companyId, Long vehicleId, Pageable pageable);

    Page<IrregularitiesTireModel> findByVehicleModelIdOrderByCreatedAtDesc(Long vehicleModelId, Pageable pageable);

    List<IrregularitiesTireModel> findByNameIrregularityAndTireSensorModelIdAndCreatedAtGreaterThanEqual(String name, Long tireId, ZonedDateTime startTime);
}
