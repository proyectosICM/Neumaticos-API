package com.icm.tiremanagementapi.repositories;

import com.icm.tiremanagementapi.models.TireChangeHistoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TireChangeHistoryRepository extends JpaRepository<TireChangeHistoryModel, Long> {
}
