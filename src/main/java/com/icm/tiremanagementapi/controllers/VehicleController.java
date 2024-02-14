package com.icm.tiremanagementapi.controllers;

import com.icm.tiremanagementapi.models.VehicleModel;
import com.icm.tiremanagementapi.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

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
     * Fetches a paginated list of all vehicles in the system.
     *
     * @param page The page number to retrieve (starting from 0).
     * @param size The size of each page.
     * @return ResponseEntity with a Page of VehicleModel objects.
     */
    @GetMapping("/page")
    public ResponseEntity<Page<VehicleModel>> getAllVehicles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<VehicleModel> vehicles = vehicleService.getAll(page, size);
        return new ResponseEntity<>(vehicles, HttpStatus.OK);
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
     * Fetches a page of vehicles associated with a specific company.
     *
     * @param companyId The ID of the company for which to retrieve vehicles.
     * @param page      The page number to retrieve (starting from 0).
     * @param size      The size of each page.
     * @return Page of VehicleModel objects associated with the specified company and matching the given status.
     */
    @GetMapping("/findByCompany")
    public Page<VehicleModel> findByCompanyId(
            @RequestParam Long companyId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        return vehicleService.findByCompanyId(companyId, page, size);
    }

    /**
     * Fetches a page of vehicles associated with a specific company and in a given state.
     *
     * @param companyId The ID of the company for which to retrieve vehicles.
     * @param status    The status of the vehicles to filter.
     * @param page      The page number to retrieve (starting from 0).
     * @param size      The size of each page.
     * @return Page of VehicleModel objects associated with the specified company and matching the given status.
     */
    @GetMapping("/findByCompanyAndStatus")
    public Page<VehicleModel> findByCompanyIdAndStatus(
            @RequestParam Long companyId,
            @RequestParam Boolean status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return vehicleService.findByCompanyIdAndStatus(companyId, status, page, size);
    }

    /**
     * Fetches a page of vehicles based on type, status, and company.
     *
     * @param typeId   The ID of the vehicle type.
     * @param status   The status of the vehicles to filter.
     * @param companyId The ID of the company for which to retrieve vehicles.
     * @param page     The page number to retrieve (starting from 0).
     * @param size     The size of each page.
     * @return Page of VehicleModel objects based on the specified type, status, and company.
     */
    @GetMapping("/findByTypeAndStatusAndCompany")
    public Page<VehicleModel> findByTypeAndStatusAndCompany(
            @RequestParam Long typeId,
            @RequestParam Boolean status,
            @RequestParam Long companyId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return vehicleService.findByVehicleTypeIdAndStatusAndCompanyId(typeId, status, companyId, page, size);
    }

    /**
     * Fetches a paginated list of vehicles based on type and company.
     *
     * @param vehicleTypeId The ID of the vehicle type.
     * @param companyId     The ID of the company for which to retrieve vehicles.
     * @param page          The page number to retrieve (starting from 0).
     * @param size          The size of each page.
     * @return Page of VehicleModel objects based on the specified type and company.
     */
    @GetMapping("/findByVehicleTypeAndCompany")
    public ResponseEntity<Page<VehicleModel>> findByVehicleTypeIdAndCompanyId(
            @RequestParam Long vehicleTypeId,
            @RequestParam Long companyId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<VehicleModel> vehicles = vehicleService.findByVehicleTypeIdAndCompanyId(vehicleTypeId, companyId, page, size);
        return new ResponseEntity<>(vehicles, HttpStatus.OK);
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
