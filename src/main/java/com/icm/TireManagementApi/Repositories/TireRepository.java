package com.icm.TireManagementApi.Repositories;

import com.icm.TireManagementApi.Models.TireModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TireRepository extends JpaRepository<TireModel, Long> {
}
