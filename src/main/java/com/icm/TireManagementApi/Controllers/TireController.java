package com.icm.TireManagementApi.Controllers;

import com.icm.TireManagementApi.Models.TireModel;
import com.icm.TireManagementApi.Services.TireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller class to manage operations related to Tire entities.
 * This service provides methods to retrieve, create, update, and delete tire records in the system.
 */
@RestController
@RequestMapping("api/tire")
public class TireController {

    @Autowired
    private TireService tireService;

    /**
     * Retrieves a list of all tires in the system.
     *
     * @return List of TireModel objects.
     */
    @GetMapping
    public List<TireModel> getAll() {
        return tireService.getAll();
    }

    /**
     * Retrieves a specific tire by its ID.
     *
     * @param id The ID of the tire to retrieve.
     * @return ResponseEntity containing the TireModel if found, otherwise returns ResponseEntity with HttpStatus.NOT_FOUND.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TireModel> getById(@PathVariable Long id) {
        Optional<TireModel> tire = tireService.getById(id);
        return tire.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Creates a new tire record in the system.
     *
     * @param tireModel The TireModel object representing the new tire.
     * @return ResponseEntity containing the created TireModel and HttpStatus.CREATED.
     */
    @PostMapping
    public ResponseEntity<TireModel> createTire(@RequestBody TireModel tireModel) {
        TireModel createdTire = tireService.createTire(tireModel);
        return new ResponseEntity<>(createdTire, HttpStatus.CREATED);
    }

    /**
     * Updates an existing tire record in the system.
     *
     * @param tireModel The updated TireModel.
     * @param id        The ID of the tire to update.
     * @return ResponseEntity containing the updated TireModel if found, otherwise returns ResponseEntity with HttpStatus.NOT_FOUND.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TireModel> updateTire(@RequestBody TireModel tireModel, @PathVariable Long id) {
        TireModel updatedTire = tireService.updateTire(tireModel, id);
        return updatedTire != null ?
                new ResponseEntity<>(updatedTire, HttpStatus.OK) :
                ResponseEntity.notFound().build();
    }

    /**
     * Deletes a tire record from the system by its ID.
     *
     * @param id The ID of the tire to delete.
     * @return ResponseEntity with HttpStatus.NO_CONTENT.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTire(@PathVariable Long id) {
        tireService.deleteTire(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
