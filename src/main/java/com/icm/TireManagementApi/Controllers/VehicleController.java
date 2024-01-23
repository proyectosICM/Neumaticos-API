package com.icm.TireManagementApi.Controllers;

import com.icm.TireManagementApi.Models.VehicleModel;
import com.icm.TireManagementApi.Services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller class to manage operations related to Vehicle entities.
 * This controller provides methods to retrieve, create, update, and delete vehicle records in the system.
 */
@RestController
@RequestMapping("api/vehicle")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    /**
     * Retrieves a list of all vehicles in the system.
     *
     * @return List of VehicleModel objects.
     */
    @GetMapping
    public List<VehicleModel> getAll() {
        return vehicleService.getAll();
    }

    /**
     * Retrieves a specific vehicle by its ID.
     *
     * @param id The ID of the vehicle to retrieve.
     * @return ResponseEntity containing the VehicleModel if found, otherwise returns ResponseEntity with HttpStatus.NOT_FOUND.
     */
    @GetMapping("/{id}")
    public ResponseEntity<VehicleModel> getById(@PathVariable Long id) {
        Optional<VehicleModel> vehicle = vehicleService.getById(id);
        return vehicle.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Creates a new vehicle record in the system.
     *
     * @param vehicleModel The VehicleModel object representing the new vehicle.
     * @return ResponseEntity containing the created VehicleModel and HttpStatus.CREATED.
     */
    @PostMapping
    public ResponseEntity<VehicleModel> createVehicle(@RequestBody VehicleModel vehicleModel) {
        VehicleModel createdVehicle = vehicleService.createVehicle(vehicleModel);
        return new ResponseEntity<>(createdVehicle, HttpStatus.CREATED);
    }

    /**
     * Updates an existing vehicle record in the system.
     *
     * @param vehicleModel The updated VehicleModel.
     * @param id           The ID of the vehicle to update.
     * @return ResponseEntity containing the updated VehicleModel if found, otherwise returns ResponseEntity with HttpStatus.NOT_FOUND.
     */
    @PutMapping("/{id}")
    public ResponseEntity<VehicleModel> updateVehicle(@RequestBody VehicleModel vehicleModel, @PathVariable Long id) {
        VehicleModel updatedVehicle = vehicleService.updateVehicle(vehicleModel, id);
        return updatedVehicle != null ?
                new ResponseEntity<>(updatedVehicle, HttpStatus.OK) :
                ResponseEntity.notFound().build();
    }

    /**
     * Deletes a vehicle record from the system by its ID.
     *
     * @param id The ID of the vehicle to delete.
     * @return ResponseEntity with HttpStatus.NO_CONTENT.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
