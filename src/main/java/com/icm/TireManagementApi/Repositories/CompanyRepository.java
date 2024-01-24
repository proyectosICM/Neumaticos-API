package com.icm.TireManagementApi.Repositories;

import com.icm.TireManagementApi.Models.CompanyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing operations related to Company entities.
 * This repository provides methods for retrieving company records from the system.
 */
@Repository
public interface CompanyRepository extends JpaRepository<CompanyModel, Long> {

    /**
     * Retrieves a list of companies based on their status.
     *
     * @param active   Boolean value indicating the status of the companies to retrieve.
     * @param pageable Pageable object for pagination.
     * @return Page of CompanyModel objects associated with the specified status.
     */
    Page<CompanyModel> findByStatus(Boolean active, Pageable pageable);

    /**
     * Retrieves a paginated list of companies by their name.
     *
     * @param name     The name of the company to retrieve.
     * @param pageable Pageable object for pagination.
     * @return Page of CompanyModel objects associated with the specified name.
     */
    Page<CompanyModel> findByName(String name, Pageable pageable);

}
