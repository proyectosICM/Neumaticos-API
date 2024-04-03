package com.icm.tiremanagementapi.repositories;

import com.icm.tiremanagementapi.models.TireModel;
import com.icm.tiremanagementapi.models.TireStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TireRepository extends JpaRepository<TireModel, Long> {
    Optional<TireModel> findByVehicleModelIdAndPositioningModelId(Long vehicleId, Long positioningId);

    Optional<TireModel> findByCodnameAndCompanyModelIdAndStatus(String codname, Long companyModelId, TireStatus status);

    List<TireModel> findByStatus(TireStatus status);

    List<TireModel> findByCompanyModelIdAndStatus(Long company, Boolean status);

    Page<TireModel> findByVehicleModelId(Long vehicle, Pageable pageable);

    Page<TireModel> findByCompanyModelId(Long companyId, Pageable pageable);

    Page<TireModel> findByVehicleModelIdAndStatus(Long vehicle, Boolean status, Pageable pageable);
}
