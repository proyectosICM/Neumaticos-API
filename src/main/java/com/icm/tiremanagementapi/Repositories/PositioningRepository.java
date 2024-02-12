package com.icm.tiremanagementapi.Repositories;

import com.icm.tiremanagementapi.models.PositioningModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PositioningRepository extends JpaRepository<PositioningModel, Long> {
    List<PositioningModel> findByVehicleTypeId(Long vehicleTypeId);
}