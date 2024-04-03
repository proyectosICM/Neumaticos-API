package com.icm.tiremanagementapi.repositories;

import com.icm.tiremanagementapi.models.VehicleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleModel, Long> {
    Optional<VehicleModel> findByPlaca(String placa);

    Page<VehicleModel> findByCompanyModelId(Long companyId, Pageable pageable);

    Page<VehicleModel> findByCompanyModelIdAndStatus(Long companyId, Boolean status, Pageable pageable);

    Page<VehicleModel> findByVehicleTypeIdAndStatusAndCompanyModelId(
            Long vehicleType, Boolean status, Long company, Pageable pageable);

    Page<VehicleModel> findByVehicleTypeIdAndCompanyModelId(Long vehicleType, Long company, Pageable pageable);
}
