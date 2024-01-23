package com.icm.TireManagementApi.Services;

import com.icm.TireManagementApi.Models.CompanyModel;
import com.icm.TireManagementApi.Models.VehicleModel;
import com.icm.TireManagementApi.Models.VehicleTypeModel;
import com.icm.TireManagementApi.Repositories.CompanyRepository;
import com.icm.TireManagementApi.Repositories.VehicleRepository;
import com.icm.TireManagementApi.Repositories.VehicleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
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
     * Retrieves a list of all vehicles in the system.
     *
     * @return List of VehicleModel objects.
     */
    public List<VehicleModel> getAll() {
        return vehicleRepository.findAll();
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
     * Retrieves a page of vehicles associated with a specific company and in a given state.
     *
     * @param companyId The ID of the company for which to retrieve vehicles.
     * @param status    The status of the vehicles to filter.
     * @param page      The page number to retrieve (starting from 0).
     * @param size      The size of each page.
     * @return Page of VehicleModel objects associated with the specified company and matching the given status.
     */
    public Page<VehicleModel> findByCompanyAndStatus(Long companyId, Boolean status, int page, int size) {
        Optional<CompanyModel> company = companyRepository.findById(companyId);

        if (company.isPresent()) {
            Pageable pageable = PageRequest.of(page, size);
            return vehicleRepository.findByCompanyAndStatus(company.get(), status, pageable);
        } else {
            // Manejar el caso en que la empresa no se encuentra
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
    public Page<VehicleModel> findByTypeAndStatusAndCompany(
            Long typeId, Boolean status, Long companyId, int page, int size) {
        Optional<VehicleTypeModel> vehicleType = vehicleTypeRepository.findById(typeId);
        Optional<CompanyModel> company = companyRepository.findById(companyId);

        if (vehicleType.isPresent() && company.isPresent()) {
            Pageable pageable = PageRequest.of(page, size);
            return vehicleRepository.findByVehicleTypeModelAndStatusAndCompany(vehicleType.get(), status, company.get(), pageable);
        } else {
            // Manejar el caso en que el tipo de vehículo o la empresa no se encuentran
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
