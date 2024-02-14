package com.icm.tiremanagementapi.controllers;

import com.icm.tiremanagementapi.models.VehicleTypeModel;
import com.icm.tiremanagementapi.services.VehicleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller class to manage operations related to VehicleType entities.
 * This controller provides methods to retrieve, create, update, and delete vehicle type records in the system.
 */
@RestController
@RequestMapping("api/vehicletypes")
public class VehicleTypeController {

    @Autowired
    private VehicleTypeService vehicleTypeService;

    /**
     * Fetches a list of all vehicle types in the system.
     *
     * @return List of VehicleTypeModel objects.
     */
    @GetMapping
    public List<VehicleTypeModel> getAll() {
        return vehicleTypeService.getAll();
    }

    /**
     * Retrieves a specific vehicle type by its ID.
     *
     * @param id The ID of the vehicle type to retrieve.
     * @return ResponseEntity containing the VehicleTypeModel if found, otherwise returns ResponseEntity with HttpStatus.NOT_FOUND.
     */
    @GetMapping("/{id}")
    public ResponseEntity<VehicleTypeModel> getById(@PathVariable Long id) {
        Optional<VehicleTypeModel> vehicleType = vehicleTypeService.getById(id);
        return vehicleType.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Creates a new vehicle type record in the system.
     *
     * @param vehicleTypeModel The VehicleTypeModel object representing the new vehicle type.
     * @return ResponseEntity containing the created VehicleTypeModel and HttpStatus.CREATED.
     */
    @PostMapping
    public ResponseEntity<VehicleTypeModel> createVehicleType(@RequestBody VehicleTypeModel vehicleTypeModel) {
        VehicleTypeModel createdVehicleType = vehicleTypeService.createVehicleType(vehicleTypeModel);
        return new ResponseEntity<>(createdVehicleType, HttpStatus.CREATED);
    }

    /**
     * Updates an existing vehicle type record in the system.
     *
     * @param vehicleTypeModel The updated VehicleTypeModel.
     * @param id               The ID of the vehicle type to update.
     * @return ResponseEntity containing the updated VehicleTypeModel if found, otherwise returns ResponseEntity with HttpStatus.NOT_FOUND.
     */
    @PutMapping("/{id}")
    public ResponseEntity<VehicleTypeModel> updateVehicleType(@RequestBody VehicleTypeModel vehicleTypeModel, @PathVariable Long id) {
        VehicleTypeModel updatedVehicleType = vehicleTypeService.updateVehicleType(vehicleTypeModel, id);
        return updatedVehicleType != null ?
                new ResponseEntity<>(updatedVehicleType, HttpStatus.OK) :
                ResponseEntity.notFound().build();
    }

    /**
     * Deletes a vehicle type record from the system by its ID.
     *
     * @param id The ID of the vehicle type to delete.
     * @return ResponseEntity with HttpStatus.NO_CONTENT.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicleType(@PathVariable Long id) {
        vehicleTypeService.deleteVehicleType(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
