package com.icm.tiremanagementapi.repositories;

import com.icm.tiremanagementapi.models.GasChangeModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface GasChangeRepository extends JpaRepository<GasChangeModel, Long> {
    List<GasChangeModel> findByVehicleModelId(Long id);

    @Query("SELECT g FROM GasChangeModel g WHERE g.vehicleModel.id = :vehicleId ORDER BY g.changeDate DESC, g.changeTime DESC")
    List<GasChangeModel> findRecentByVehicleModelId(@Param("vehicleId") Long vehicleId, Pageable pageable);

    @Query("SELECT g FROM GasChangeModel g WHERE g.vehicleModel.id = :vehicleId ORDER BY g.changeDate DESC, g.changeTime DESC")
    Page<GasChangeModel> findByVehicleModelIdOrderByDesc(@Param("vehicleId") Long vehicleId, Pageable pageable);

}
