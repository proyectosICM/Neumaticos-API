package com.icm.tiremanagementapi.Services;

import com.icm.tiremanagementapi.models.CompanyModel;
import com.icm.tiremanagementapi.Repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service class for managing operations related to Company entities.
 * This service provides methods for retrieving, creating, updating, and deleting company records in the system.
 */
@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    /**
     * Retrieves a paginated list of all companies in the system.
     *
     * @param pageable Pageable object for pagination.
     * @return Page of CompanyModel objects.
     */
    public Page<CompanyModel> getAll(Pageable pageable) {
        return companyRepository.findAll(pageable);
    }

    /**
     * Retrieves a specific company by its ID.
     *
     * @param id The ID of the company to retrieve.
     * @return Optional containing the CompanyModel if found, otherwise empty.
     */
    public Optional<CompanyModel> getById(Long id) {
        return companyRepository.findById(id);
    }

    /**
     * Retrieves a paginated list of companies based on their status.
     *
     * @param active   Boolean value indicating the status of the companies to retrieve.
     * @param pageable Pageable object for pagination.
     * @return Page of CompanyModel objects associated with the specified status.
     */
    public Page<CompanyModel> findByStatus(Boolean active, Pageable pageable) {
        return companyRepository.findByStatus(active, pageable);
    }


    /**
     * Retrieves a paginated list of companies by their name.
     *
     * @param name     The name of the company to retrieve.
     * @param pageable Pageable object for pagination.
     * @return Page of CompanyModel objects associated with the specified name.
     */
    public Page<CompanyModel> findByName(String name, Pageable pageable) {
        return companyRepository.findByName(name, pageable);
    }

    /**
     * Creates a new company record in the system.
     *
     * @param company The CompanyModel object representing the new company.
     * @return The created CompanyModel.
     */
    public CompanyModel createCompany(CompanyModel company) {
        return companyRepository.save(company);
    }

    /**
     * Updates an existing company record in the system, including the name and optionally the status.
     *
     * @param company The updated CompanyModel.
     * @param id      The ID of the company to update.
     * @return The updated CompanyModel if the company with the given ID is found, otherwise null.
     */
    public CompanyModel updateCompany (CompanyModel company, Long id) {
        return companyRepository.findById(id)
                .map(existingCompany -> {
                    existingCompany.setName(company.getName());

                    // Check if a status value is provided before updating
                    if (company.getStatus() != null) {
                        existingCompany.setStatus(company.getStatus());
                    }

                    return companyRepository.save(existingCompany);
                })
                .orElse(null);
    }

    /**
     * Updates the status of an existing company record in the system.
     *
     * @param id     The ID of the company to update.
     * @param status The new status to set.
     * @return The updated CompanyModel if the company with the given ID is found, otherwise null.
     */
    public CompanyModel updateCompanyStatus(Long id, Boolean status) {
        return companyRepository.findById(id)
                .map(existingCompany -> {
                    existingCompany.setStatus(status);
                    return companyRepository.save(existingCompany);
                })
                .orElse(null);
    }


    /**
     * Deletes a company record from the system by its ID.
     *
     * @param id The ID of the company to delete.
     */
    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }
}

