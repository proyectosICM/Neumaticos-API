package com.icm.TireManagementApi.Services;

import com.icm.TireManagementApi.Models.VehicleModel;
import com.icm.TireManagementApi.Repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
