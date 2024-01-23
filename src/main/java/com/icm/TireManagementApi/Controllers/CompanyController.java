package com.icm.TireManagementApi.Controllers;

import com.icm.TireManagementApi.Models.CompanyModel;
import com.icm.TireManagementApi.Services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    /**
     * Retrieves a list of all companies in the system.
     *
     * @return List of CompanyModel objects.
     */
    @GetMapping
    public List<CompanyModel> getAll() {
        return companyService.getAll();
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