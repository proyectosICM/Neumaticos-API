package com.icm.tiremanagementapi.controllers;

import com.icm.tiremanagementapi.models.CompanyModel;
import com.icm.tiremanagementapi.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Handles HTTP requests related to company.
 * Supports operations such as listing, retrieving, creating, updating, and deleting companies.
 */
@RestController
@RequestMapping("api/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    /**
     * Fetches a paginated list of all companies.
     *
     * @return list of companies.
     */
    @GetMapping()
    public List<CompanyModel> getAllCompanies() {
        return companyService.getAll();
    }

    /**
     * Fetches a paginated list of all companies.
     *
     * @param page The page number to retrieve, default is 0.
     * @param size The size of the page, default is 6.
     * @return Paginated list of companies.
     */
    @GetMapping("/page")
    public Page<CompanyModel> getAllCompaniesPaginated(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "6") int size) {
        return companyService.getAll(page, size);
    }

    /**
     * Retrieves a specific company by its ID.
     *
     * @param id The ID of the company.
     * @return The requested company if found, or HTTP 404 if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CompanyModel> getById(@PathVariable Long id) {
        Optional<CompanyModel> company = companyService.getById(id);
        return company.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Fetches a list of companies based on their status.
     *
     * @param active Boolean value indicating the status of the companies to retrieve.
     * @return ResponseEntity containing the paginated list of irregularities and the HTTP status.
     */
    @GetMapping("/status")
    public List<CompanyModel> getCompaniesByStatusPaginated(@RequestParam Boolean active) {
        return companyService.findByStatus(active);
    }

    /**
     * Fetches a paginated list of companies based on their status.
     *
     * @param active   Boolean value indicating the status of the companies to retrieve.
     * @param page The page number to retrieve, default is 0.
     * @param size The size of the page, default is 6.
     * @return ResponseEntity containing the paginated list of irregularities and the HTTP status.
     */
    @GetMapping("/status/page")
    public Page<CompanyModel> getCompaniesByStatusPaginated(@RequestParam Boolean active,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "6") int size) {
        return companyService.findByStatus(active, page, size);
    }

    /**
     * Fetches a paginated list of companies by their name.
     *
     * @param name     The name of the company to retrieve.
     * @return Page of CompanyModel objects associated with the specified name.
     */
    @GetMapping("/name/page")
    public List<CompanyModel> getCompaniesByNamePaginated(@RequestParam String name) {
        return companyService.findByName(name);
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
    @PatchMapping("status/{id}")
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