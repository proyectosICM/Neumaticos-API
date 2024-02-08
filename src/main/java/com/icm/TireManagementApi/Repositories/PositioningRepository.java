package com.icm.TireManagementApi.Repositories;

import com.icm.TireManagementApi.Models.PositioningModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PositioningRepository extends JpaRepository<PositioningModel, Long> {
    List<PositioningModel> findByVehicleTypeId(Long vehicleTypeId);
}