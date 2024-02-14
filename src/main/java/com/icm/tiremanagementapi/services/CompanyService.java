package com.icm.tiremanagementapi.services;

import com.icm.tiremanagementapi.models.CompanyModel;
import com.icm.tiremanagementapi.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.util.List;
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
     * Retrieves list of all companies in the system.
     *
     * @return Page of CompanyModel objects.
     */
    public List<CompanyModel> getAll() {
        return companyRepository.findAll();
    }

    /**
     * Retrieves a paginated list of all companies in the system.
     *
     * @param page The page number to retrieve.
     * @param size The size of the page.
     * @return Page of CompanyModel objects.
     */
    public Page<CompanyModel> getAll(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
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
     * Retrieves a list of companies based on their status.
     *
     * @param active   Boolean value indicating the status of the companies to retrieve.
     * @return Page of CompanyModel objects associated with the specified status.
     */
    public List<CompanyModel> findByStatus(Boolean active) {
        return companyRepository.findByStatus(active);
    }

    /**
     * Retrieves a paginated list of companies based on their status.
     *
     * @param active   Boolean value indicating the status of the companies to retrieve.
     * @param page The page number to retrieve.
     * @param size The size of the page.
     * @return Page of CompanyModel objects associated with the specified status.
     */
    public Page<CompanyModel> findByStatus(Boolean active, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return companyRepository.findByStatus(active, pageable);
    }


    /**
     * Retrieves a  list of companies by their name.
     *
     * @param name     The name of the company to retrieve.
     *
     * @return Page of CompanyModel objects associated with the specified name.
     */
    public List<CompanyModel> findByName(String name) {
        return companyRepository.findByName(name);
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


