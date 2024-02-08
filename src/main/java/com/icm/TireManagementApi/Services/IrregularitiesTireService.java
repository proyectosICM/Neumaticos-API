package com.icm.TireManagementApi.Services;

import com.icm.TireManagementApi.Models.IrregularitiesTireModel;
import com.icm.TireManagementApi.Repositories.IrregularitiesTireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for managing operations related to IrregularitiesTire entities.
 * This service provides methods for retrieving, creating, updating, and deleting irregularity records in the system.
 */
@Service
public class IrregularitiesTireService {
    @Autowired
    private IrregularitiesTireRepository irregularitiesTireRepository;


    /**
     * Retrieves a paginated list of all tire irregularities in the system.
     *
     * @param pageable Pageable object for pagination.
     * @return Page of IrregularitiesTireModel objects.
     */
    public Page<IrregularitiesTireModel> getAllIrregularities(Pageable pageable) {
        return irregularitiesTireRepository.findAll(pageable);
    }

    /**
     * Retrieves a paginated list of all tire irregularities associated with a specific company.
     *
     * @param companyId The ID of the company.
     * @param pageable  The pageable information for pagination.
     * @return Page of IrregularitiesTireModel objects.
     */
    public Page<IrregularitiesTireModel> findIrregularitiesByCompanyId(Long companyId, Pageable pageable) {
        return irregularitiesTireRepository.findByCompanyId(companyId, pageable);
    }

    /**
     * Retrieves a paginated list of all tire irregularities associated with a specific company and vehicle.
     *
     * @param companyId The ID of the company.
     * @param vehicleId The ID of the vehicle.
     * @param pageable  The pageable information for pagination.
     * @return Page of IrregularitiesTireModel objects.
     */
    public Page<IrregularitiesTireModel> findIrregularitiesByCompanyIdAndVehicleId(Long companyId, Long vehicleId, Pageable pageable) {
        return irregularitiesTireRepository.findByCompanyIdAndVehicleModelId(companyId, vehicleId, pageable);
    }

    public Page<IrregularitiesTireModel> findRecentIrregularities() {
        Pageable topSix = PageRequest.of(0, 6, Sort.by(Sort.Direction.DESC, "createdAt"));
        return irregularitiesTireRepository.findTop6ByOrderByCreatedAtDesc(topSix);
    }

    /**
     * Retrieves a specific tire irregularity by its ID.
     *
     * @param id The ID of the irregularity to retrieve.
     * @return Optional containing the IrregularitiesTireModel if found, otherwise empty.
     */
    public Optional<IrregularitiesTireModel> getIrregularityById(Long id) {
        return irregularitiesTireRepository.findById(id);
    }

    /**
     * Creates a new tire irregularity record in the system.
     *
     * @param irregularity The IrregularitiesTireModel object representing the new irregularity.
     * @return The created IrregularitiesTireModel.
     */
    public IrregularitiesTireModel createIrregularity(IrregularitiesTireModel irregularity) {
        return irregularitiesTireRepository.save(irregularity);
    }

    /**
     * Updates an existing tire irregularity record in the system.
     *
     * @param irregularity The updated IrregularitiesTireModel.
     * @param id           The ID of the irregularity to update.
     * @return The updated IrregularitiesTireModel if the irregularity with the given ID is found, otherwise null.
     */
    public IrregularitiesTireModel updateIrregularity(IrregularitiesTireModel irregularity, Long id) {
        return irregularitiesTireRepository.findById(id)
                .map(existingIrregularity -> {
                    existingIrregularity.setVehicleModel(irregularity.getVehicleModel());
                    existingIrregularity.setCompany(irregularity.getCompany());
                    existingIrregularity.setStatus(irregularity.getStatus());
                    return irregularitiesTireRepository.save(existingIrregularity);
                })
                .orElse(null);
    }

    /**
     * Deletes a tire irregularity record from the system by its ID.
     *
     * @param id The ID of the irregularity to delete.
     */
    public void deleteIrregularity(Long id) {
        irregularitiesTireRepository.deleteById(id);
    }
}
