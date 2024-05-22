package com.icm.tiremanagementapi.repositories;

import com.icm.tiremanagementapi.models.GasChangeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GasChangeRepository extends JpaRepository<GasChangeModel, Long> {
    List<GasChangeModel>
}
