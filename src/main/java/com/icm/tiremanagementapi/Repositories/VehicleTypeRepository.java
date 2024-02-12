package com.icm.tiremanagementapi.Repositories;

import com.icm.tiremanagementapi.models.VehicleTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleTypeRepository extends JpaRepository<VehicleTypeModel, Long> {
}
