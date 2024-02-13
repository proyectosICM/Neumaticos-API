package com.icm.tiremanagementapi.controllers;

import com.icm.tiremanagementapi.models.PositioningModel;
import com.icm.tiremanagementapi.Services.PositioningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles HTTP requests related to tire positioning.
 * Supports operations such as listing, retrieving, creating, updating, and deleting irregularities.
 */
@RestController
@RequestMapping("api/positioning")
public class PositioningController {

    @Autowired
    private  PositioningService positioningService;

    /**
     * Fetches a paginated list of all positioning.
     *
     * @return list of positioning.
     */
    @GetMapping
    public ResponseEntity<List<PositioningModel>> getAllPositionings() {
        List<PositioningModel> positionings = positioningService.findAllPositionings();
        return new ResponseEntity<>(positionings, HttpStatus.OK);
    }

    /**
     * Retrieves a specific positioning by its ID.
     *
     * @param id The ID of the positioning.
     * @return The requested positioning if found, or HTTP 404 if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PositioningModel> getPositioningById(@PathVariable Long id) {
        return positioningService.findPositioningById(id)
                .map(positioning -> new ResponseEntity<>(positioning, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Fetches a list of companies based on their vehicle Type.
     *
     * @param vehicleTypeId The ID of the vehicle type for which to retrieve tire positioning records.
     * @return ResponseEntity containing the paginated list of irregularities and the HTTP status.
     */
    @GetMapping("/vehicleType")
    public ResponseEntity<List<PositioningModel>> getPositioningsByVehicleTypeId(@RequestParam Long vehicleTypeId) {
        List<PositioningModel> positionings = positioningService.findPositioningsByVehicleTypeId(vehicleTypeId);
        if (positionings.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(positionings);
    }

    /**
     * Creates a new positioning record in the system.
     *
     * @param positioningModel The PositioningModel object representing the new positioning.
     * @return ResponseEntity containing the created PositioningModel and HttpStatus.CREATED.
     */
    @PostMapping
    public ResponseEntity<PositioningModel> createPositioning(@RequestBody PositioningModel positioningModel) {
        PositioningModel newPositioning = positioningService.savePositioning(positioningModel);
        return new ResponseEntity<>(newPositioning, HttpStatus.CREATED);
    }

    /**
     * Updates an existing positioning record in the system.
     *
     * @param positioning The updated PositioningModel.
     * @param id           The ID of the positioning to update.
     * @return ResponseEntity containing the updated PositioningModel if found, otherwise returns ResponseEntity with HttpStatus.NOT_FOUND.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PositioningModel> updatePositioning(@PathVariable Long id, @RequestBody PositioningModel positioning) {
        return positioningService.findPositioningById(id)
                .map(existingPositioning -> {
                    positioning.setId(id);
                    PositioningModel updatedPositioning = positioningService.savePositioning(positioning);
                    return new ResponseEntity<>(updatedPositioning, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Deletes a positioning record from the system by its ID.
     *
     * @param id The ID of the positioning to delete.
     * @return ResponseEntity with HttpStatus.NO_CONTENT.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePositioning(@PathVariable Long id) {
        positioningService.deletePositioning(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}