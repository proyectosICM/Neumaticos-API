package com.icm.tiremanagementapi.repositories;

import com.icm.tiremanagementapi.models.TireSensorModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TireSensorRepository extends JpaRepository<TireSensorModel, Long> {
    Optional<TireSensorModel> findByIdentificationCode(String code);

    Optional<TireSensorModel> findByVehicleModelIdAndPositioningId(Long vehicle, Long positioning);

    Optional<TireSensorModel> findByIdAndCompanyModelIdAndStatus(Long id, Long companyModelId, Boolean status);

    List<TireSensorModel> findByCompanyModelIdAndStatus(Long company, Boolean status);

    List<TireSensorModel> findByVehicleModelIdAndPositioningLocationCode(Long vehicle, String positioning);

    List<TireSensorModel> findByVehicleModelId(Long vehicle);

    Page<TireSensorModel> findByVehicleModelId(Long vehicle, Pageable pageable);

    Page<TireSensorModel> findByCompanyModelId(Long companyId, Pageable pageable);

    Page<TireSensorModel> findByVehicleModelIdAndStatus(Long vehicle, Boolean status, Pageable pageable);
}
