package com.icm.tiremanagementapi.repositories;

import com.icm.tiremanagementapi.models.PositioningModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PositioningRepository extends JpaRepository<PositioningModel, Long> {
    List<PositioningModel> findByVehicleTypeId(Long vehicleTypeId);
    PositioningModel findByLocationCode(String locationCode);
}