package com.icm.TireManagementApi.Repositories;

import com.icm.TireManagementApi.Models.VehicleModel;
import com.icm.TireManagementApi.Models.VehicleTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleModel, Long> {
}
