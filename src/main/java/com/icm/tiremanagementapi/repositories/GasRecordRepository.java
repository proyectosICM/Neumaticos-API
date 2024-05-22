package com.icm.tiremanagementapi.repositories;

import com.icm.tiremanagementapi.models.GasRecordModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GasRecordRepository extends JpaRepository<GasRecordModel, Long> {
}
