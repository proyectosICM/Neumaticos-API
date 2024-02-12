package com.icm.tiremanagementapi.Services;

import com.icm.tiremanagementapi.models.VehicleTypeModel;
import com.icm.tiremanagementapi.Repositories.VehicleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing operations related to VehicleType entities.
 * This service provides methods for retrieving, creating, updating, and deleting vehicle type records in the system.
 */
@Service
public class VehicleTypeService {
    @Autowired
    private VehicleTypeRepository vehicleTypeRepository;

    /**
     * Retrieves a list of all vehicle types in the system.
     *
     * @return List of VehicleTypeModel objects.
     */
    public List<VehicleTypeModel> getAll() {
        return vehicleTypeRepository.findAll();
    }

    /**
     * Retrieves a specific vehicle type by its ID.
     *
     * @param id The ID of the vehicle type to retrieve.
     * @return Optional containing the VehicleTypeModel if found, otherwise empty.
     */
    public Optional<VehicleTypeModel> getById(Long id) {
        return vehicleTypeRepository.findById(id);
    }

    /**
     * Creates a new vehicle type record in the system.
     *
     * @param vehicleType The VehicleTypeModel object representing the new vehicle type.
     * @return The created VehicleTypeModel.
     */
    public VehicleTypeModel createVehicleType(VehicleTypeModel vehicleType) {
        return vehicleTypeRepository.save(vehicleType);
    }

    /**
     * Updates an existing vehicle type record in the system.
     *
     * @param vehicleType The updated VehicleTypeModel.
     * @param id          The ID of the vehicle type to update.
     * @return The updated VehicleTypeModel if the vehicle type with the given ID is found, otherwise null.
     */
    public VehicleTypeModel updateVehicleType(VehicleTypeModel vehicleType, Long id) {
        return vehicleTypeRepository.findById(id)
                .map(existingVehicleType -> {
                    existingVehicleType.setName(vehicleType.getName());
                    return vehicleTypeRepository.save(existingVehicleType);
                })
                .orElse(null);
    }

    /**
     * Deletes a vehicle type record from the system by its ID.
     *
     * @param id The ID of the vehicle type to delete.
     */
    public void deleteVehicleType(Long id) {
        vehicleTypeRepository.deleteById(id);
    }
}
