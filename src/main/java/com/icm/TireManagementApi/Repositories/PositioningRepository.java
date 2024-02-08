package com.icm.TireManagementApi.Repositories;

import com.icm.TireManagementApi.Models.PositioningModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositioningRepository extends JpaRepository<PositioningModel, Long> {
}
