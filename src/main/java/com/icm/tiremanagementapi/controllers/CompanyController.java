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

@RestController
@RequestMapping("api/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/{id}")
    public ResponseEntity<CompanyModel> findById(@PathVariable Long id) {
        Optional<CompanyModel> company = companyService.findById(id);
        return company.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/name/page")
    public List<CompanyModel> findByName(@RequestParam String name) {
        return companyService.findByName(name);
    }

    @GetMapping
    public List<CompanyModel> findAll() {
        return companyService.findAll();
    }

    @GetMapping("/page")
    public Page<CompanyModel> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        return companyService.findAll(page, size);
    }

    @GetMapping("/status")
    public List<CompanyModel> findByStatus(@RequestParam Boolean active) {
        return companyService.findByStatus(active);
    }

    @GetMapping("/status/page")
    public Page<CompanyModel> findByStatus(@RequestParam Boolean active,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "6") int size) {
        return companyService.findByStatus(active, page, size);
    }

    @PostMapping
    public ResponseEntity<CompanyModel> createCompany(@RequestBody CompanyModel companyModel) {
        CompanyModel createdCompany = companyService.createCompany(companyModel);
        return new ResponseEntity<>(createdCompany, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyModel> updateCompany(@RequestBody CompanyModel companyModel, @PathVariable Long id) {
        CompanyModel updatedCompany = companyService.updateCompany(companyModel, id);
        return updatedCompany != null ?
                new ResponseEntity<>(updatedCompany, HttpStatus.OK) :
                ResponseEntity.notFound().build();
    }

    @PatchMapping("status/{id}")
    public ResponseEntity<CompanyModel> updateCompanyStatus(@PathVariable Long id, @RequestParam Boolean status) {
        CompanyModel updatedCompany = companyService.updateCompanyStatus(id, status);
        return updatedCompany != null ?
                new ResponseEntity<>(updatedCompany, HttpStatus.OK) :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}