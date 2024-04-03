package com.icm.tiremanagementapi.repositories;

import com.icm.tiremanagementapi.models.CompanyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyModel, Long> {
    List<CompanyModel> findByName(String name);

    List<CompanyModel> findByStatus(Boolean active);

    Page<CompanyModel> findByStatus(Boolean active, Pageable pageable);
}
