package com.icm.tiremanagementapi.services;

import com.icm.tiremanagementapi.models.CompanyModel;
import com.icm.tiremanagementapi.repositories.CompanyRepository;
import com.icm.tiremanagementapi.services.utils.DirectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private DirectoryService directoryService;

    @Value("${file.image}")
    private String basePath;

    public Optional<CompanyModel> findById(Long id) {
        return companyRepository.findById(id);
    }

    public List<CompanyModel> findByName(String name) {
        return companyRepository.findByName(name);
    }

    public List<CompanyModel> findAll() {
        return companyRepository.findAll();
    }

    public Page<CompanyModel> findAll(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return companyRepository.findAll(pageable);
    }

    public List<CompanyModel> findByStatus(Boolean active) {
        return companyRepository.findByStatus(active);
    }

    public Page<CompanyModel> findByStatus(Boolean active, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return companyRepository.findByStatus(active, pageable);
    }

    public CompanyModel createCompany(CompanyModel company) {
        // Guarda la empresa en la base de datos.
        CompanyModel savedCompany = companyRepository.save(company);

        // Crea el directorio para la empresa.
        directoryService.createDirectoryWithName(basePath, savedCompany.getName());
        String irregularidadesPath = basePath + File.separator + savedCompany.getName() + File.separator;
        directoryService.createDirectoryWithName(irregularidadesPath, "irregularidades");
        return savedCompany;
    }

    public CompanyModel updateCompany(CompanyModel company, Long id) {
        return companyRepository.findById(id).map(existingCompany -> {
            existingCompany.setName(company.getName());

            // Check if a status value is provided before updating
            if (company.getStatus() != null) {
                existingCompany.setStatus(company.getStatus());
            }

            return companyRepository.save(existingCompany);
        }).orElse(null);
    }

    public CompanyModel updateCompanyStatus(Long id, Boolean status) {
        return companyRepository.findById(id).map(existingCompany -> {
            existingCompany.setStatus(status);
            return companyRepository.save(existingCompany);
        }).orElse(null);
    }

    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }
}


