package com.icm.tiremanagementapi.Services;

import com.icm.tiremanagementapi.models.IrregularitiesTireModel;
import com.icm.tiremanagementapi.Repositories.IrregularitiesTireRepository;
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
     * This method creates a Pageable object based on the page number and page size parameters to control pagination.
     *
     * @param companyId The ID of the company for which to retrieve irregularities.
     * @param page The page number of the requested page.
     * @param size The size of the requested page.
     * @return A Page of IrregularitiesTireModel objects for the specified company.
     */
    public Page<IrregularitiesTireModel> findIrregularitiesByCompanyId(Long companyId, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
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

    /**
     * Retrieves the latest 6 irregularities for a specific vehicle model.
     * This method uses Spring Data JPA's method naming conventions to automatically generate the query,
     * limiting the results to the top 6 most recent entries based on the 'createdAt' timestamp.
     *
     * @param vehicleModelId The ID of the vehicle model for which to retrieve irregularities.
     * @return A page of IrregularitiesTireModel containing the latest 6 irregularities for the specified vehicle model.
     */
    public Page<IrregularitiesTireModel> findRecentIrregularitiesByVehicleModelId(Long vehicleModelId) {
        Pageable topSix = PageRequest.of(0, 6, Sort.by(Sort.Direction.DESC, "createdAt"));
        return irregularitiesTireRepository.findByVehicleModelIdOrderByCreatedAtDesc(vehicleModelId, topSix);
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
