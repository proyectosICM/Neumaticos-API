package com.icm.TireManagementApi.Repositories;

import com.icm.TireManagementApi.Models.VehicleTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleTypeRepository extends JpaRepository<VehicleTypeModel, Long> {
}
