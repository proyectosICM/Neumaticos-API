package com.icm.TireManagementApi.Controllers;

import com.icm.TireManagementApi.Models.CompanyModel;
import com.icm.TireManagementApi.Services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller class to manage operations related to Company entities.
 * This service provides methods to retrieve, create, update, and delete company records in the system.
 */
@RestController
@RequestMapping("api/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping
    public List<CompanyModel> getAllCompanies() {
        return companyService.getAll();
    }

    /**
     * Retrieves a paginated list of all companies in the system.
     *
     * @param pageable Pageable object for pagination.
     * @return Page of CompanyModel objects.
     */
    @GetMapping("/page")
    public Page<CompanyModel> getAllCompaniesPaginated(Pageable pageable) {
        return companyService.getAll(pageable);
    }


    /**
     * Retrieves a specific company by its ID.
     *
     * @param id The ID of the company to retrieve.
     * @return ResponseEntity containing the CompanyModel if found, otherwise returns ResponseEntity with HttpStatus.NOT_FOUND.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CompanyModel> getById(@PathVariable Long id) {
        Optional<CompanyModel> company = companyService.getById(id);
        return company.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves a list of companies based on their status.
     *
     * @param active Boolean value indicating the status of the companies to retrieve.
     * @return List of CompanyModel objects associated with the specified status.
     */
    @GetMapping("/status")
    public List<CompanyModel> getCompaniesByStatus(@RequestParam Boolean active) {
        return companyService.findByStatus(active);
    }

    /**
     * Retrieves a paginated list of companies based on their status.
     *
     * @param active   Boolean value indicating the status of the companies to retrieve.
     * @param pageable Pageable object for pagination.
     * @return Page of CompanyModel objects associated with the specified status.
     */
    @GetMapping("/status/page")
    public Page<CompanyModel> getCompaniesByStatusPaginated(@RequestParam Boolean active, Pageable pageable) {
        return companyService.findByStatus(active, pageable);
    }

    /**
     * Retrieves a list of companies by their name.
     *
     * @param name The name of the company to retrieve.
     * @return List of CompanyModel objects associated with the specified name.
     */
    @GetMapping("/name")
    public List<CompanyModel> getCompaniesByName(@RequestParam String name) {
        return companyService.findByName(name);
    }

    /**
     * Retrieves a paginated list of companies by their name.
     *
     * @param name     The name of the company to retrieve.
     * @param pageable Pageable object for pagination.
     * @return Page of CompanyModel objects associated with the specified name.
     */
    @GetMapping("/name/page")
    public Page<CompanyModel> getCompaniesByNamePaginated(@RequestParam String name, Pageable pageable) {
        return companyService.findByName(name, pageable);
    }


    /**
     * Creates a new company record in the system.
     *
     * @param companyModel The CompanyModel object representing the new company.
     * @return ResponseEntity containing the created CompanyModel and HttpStatus.CREATED.
     */
    @PostMapping
    public ResponseEntity<CompanyModel> createCompany(@RequestBody CompanyModel companyModel) {
        CompanyModel createdCompany = companyService.createCompany(companyModel);
        return new ResponseEntity<>(createdCompany, HttpStatus.CREATED);
    }

    /**
     * Updates an existing company record in the system.
     *
     * @param companyModel The updated CompanyModel.
     * @param id           The ID of the company to update.
     * @return ResponseEntity containing the updated CompanyModel if found, otherwise returns ResponseEntity with HttpStatus.NOT_FOUND.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CompanyModel> updateCompany (@RequestBody CompanyModel companyModel, @PathVariable Long id) {
        CompanyModel updatedCompany = companyService.updateCompany (companyModel, id);
        return updatedCompany != null ?
                new ResponseEntity<>(updatedCompany, HttpStatus.OK) :
                ResponseEntity.notFound().build();
    }

    /**
     * Updates the status of an existing company record in the system.
     *
     * @param id     The ID of the company to update.
     * @param status The new status to set.
     * @return ResponseEntity containing the updated CompanyModel if the company with the given ID is found,
     * otherwise returns ResponseEntity with HttpStatus.NOT_FOUND.
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<CompanyModel> updateCompanyStatus(@PathVariable Long id, @RequestParam Boolean status) {
        CompanyModel updatedCompany = companyService.updateCompanyStatus(id, status);
        return updatedCompany != null ?
                new ResponseEntity<>(updatedCompany, HttpStatus.OK) :
                ResponseEntity.notFound().build();
    }

    /**
     * Deletes a company record from the system by its ID.
     *
     * @param id The ID of the company to delete.
     * @return ResponseEntity with HttpStatus.NO_CONTENT.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}