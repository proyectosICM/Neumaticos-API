package com.icm.TireManagementApi.Repositories;

import com.icm.TireManagementApi.Models.CompanyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyModel, Long> {
    List<CompanyModel> findByIdAndStatus(Long id, Boolean active);
    List<CompanyModel> findByStatus(Boolean active);
}
