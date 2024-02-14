package com.icm.tiremanagementapi.services;

import com.icm.tiremanagementapi.models.VehicleModel;
import com.icm.tiremanagementapi.repositories.CompanyRepository;
import com.icm.tiremanagementapi.repositories.VehicleRepository;
import com.icm.tiremanagementapi.repositories.VehicleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service class for managing operations related to Vehicle entities.
 * This service provides methods for retrieving, creating, updating, and deleting vehicle records in the system.
 */
@Service
public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private VehicleTypeRepository vehicleTypeRepository;

    /**
     * Retrieves a paginated list of all vehicles in the system.
     *
     * @param page The page number to retrieve (starting from 0).
     * @param size The size of each page.
     * @return Page of VehicleModel objects.
     */
    public Page<VehicleModel> getAll(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            return vehicleRepository.findAll(pageable);
        } catch (Exception e) {
            // Log or handle the exception appropriately
            e.printStackTrace();
            throw new RuntimeException("Error retrieving vehicles", e);
        }
    }

    /**
     * Retrieves a specific vehicle by its ID.
     *
     * @param id The ID of the vehicle to retrieve.
     * @return Optional containing the VehicleModel if found, otherwise empty.
     */
    public Optional<VehicleModel> getById(Long id) {
        return vehicleRepository.findById(id);
    }
    /**
     * Retrieves a page of vehicles associated with a specific company.
     *
     * @param companyId The ID of the company for which to retrieve vehicles.
     * @param page      The page number to retrieve (starting from 0).
     * @param size      The size of each page.
     * @return Page of VehicleModel objects associated with the specified company and matching the given status.
     */
    public Page<VehicleModel> findByCompanyId(Long companyId, int page, int size) {
        if (companyId != null) {
            Pageable pageable = PageRequest.of(page, size);
            return vehicleRepository.findByCompanyId(companyId, pageable);
        } else {
            return Page.empty();
        }
    }

    /**
     * Retrieves a page of vehicles associated with a specific company and in a given state.
     *
     * @param companyId The ID of the company for which to retrieve vehicles.
     * @param status    The status of the vehicles to filter.
     * @param page      The page number to retrieve (starting from 0).
     * @param size      The size of each page.
     * @return Page of VehicleModel objects associated with the specified company and matching the given status.
     */
    public Page<VehicleModel> findByCompanyIdAndStatus(Long companyId, Boolean status, int page, int size) {
        if (companyId != null) {
            Pageable pageable = PageRequest.of(page, size);
            return vehicleRepository.findByCompanyIdAndStatus(companyId, status, pageable);
        } else {
            return Page.empty();
        }
    }

    /**
     * Retrieves a page of vehicles based on type, status, and company.
     *
     * @param typeId   The ID of the vehicle type.
     * @param status   The status of the vehicles to filter.
     * @param companyId The ID of the company for which to retrieve vehicles.
     * @param page     The page number to retrieve (starting from 0).
     * @param size     The size of each page.
     * @return Page of VehicleModel objects based on the specified type, status, and company.
     */
    public Page<VehicleModel> findByVehicleTypeIdAndStatusAndCompanyId(
            Long typeId, Boolean status, Long companyId, int page, int size) {
        if (typeId != null && companyId != null) {
            Pageable pageable = PageRequest.of(page, size);
            return vehicleRepository.findByVehicleTypeIdAndStatusAndCompanyId(typeId, status, companyId, pageable);
        } else {
            return Page.empty();
        }
    }

    /**
     * Retrieves a paginated list of vehicles based on type and company.
     *
     * @param vehicleTypeId The ID of the vehicle type.
     * @param companyId     The ID of the company for which to retrieve vehicles.
     * @param page          The page number to retrieve (starting from 0).
     * @param size          The size of each page.
     * @return Page of VehicleModel objects based on the specified type and company.
     */
    public Page<VehicleModel> findByVehicleTypeIdAndCompanyId(Long vehicleTypeId, Long companyId, int page, int size) {
        if (vehicleTypeId != null && companyId != null) {
            Pageable pageable = PageRequest.of(page, size);
            return vehicleRepository.findByVehicleTypeIdAndCompanyId(vehicleTypeId, companyId, pageable);
        } else {
            return Page.empty();
        }
    }


    /**
     * Creates a new vehicle record in the system.
     *
     * @param vehicle The VehicleModel object representing the new vehicle.
     * @return The created VehicleModel.
     */
    public VehicleModel createVehicle(VehicleModel vehicle) {
        return vehicleRepository.save(vehicle);
    }

    /**
     * Updates an existing vehicle record in the system.
     *
     * @param vehicle The updated VehicleModel.
     * @param id      The ID of the vehicle to update.
     * @return The updated VehicleModel if the vehicle with the given ID is found, otherwise null.
     */
    public VehicleModel updateVehicle(VehicleModel vehicle, Long id) {
        return vehicleRepository.findById(id)
                .map(existingVehicle -> {
                    // Add any additional update logic here if needed
                    return vehicleRepository.save(existingVehicle);
                })
                .orElse(null);
    }

    /**
     * Deletes a vehicle record from the system by its ID.
     *
     * @param id The ID of the vehicle to delete.
     */
    public void deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
    }
}
