package com.icm.tiremanagementapi.controllers;

import com.icm.tiremanagementapi.models.TireModel;
import com.icm.tiremanagementapi.models.TireStatus;
import com.icm.tiremanagementapi.services.TireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

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

    @GetMapping("/search")
    public ResponseEntity<TireModel> findByCodnameAndCompanyModelId(@RequestParam String codname, @RequestParam Long companyModelId) {
        Optional<TireModel> tire = tireService.findByCodnameAndCompanyModelIdAndStatus(codname, companyModelId);
        return tire.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
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
     * Retrieves a list of all tires associated with a specific vehicle.
     * This method is designed to fetch a comprehensive list without pagination,
     * suitable for scenarios where the complete set of associated tires is required.
     *
     * @param vehicleId The ID of the vehicle for which to retrieve the associated tires.
     * @return ResponseEntity containing the list of TireModel objects associated with the specified vehicle ID.
     */
    @GetMapping("/vehicle")
    public ResponseEntity<List<TireModel>> findTiresByVehicleId(
                                                                @RequestParam("status") TireStatus status) {
        List<TireModel> tires = tireService.findTiresByVehicleId( status);
        if(tires.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tires, HttpStatus.OK);
    }

    /**
     * Retrieves a list of tires associated with a specific vehicle using pagination.
     *
     * @param vehicleId The ID of the vehicle for which to retrieve tires.
     * @param page      The page number (starts from 0).
     * @param size      The number of items per page.
     * @return Page of TireModel objects associated with the specified vehicle.
     */
    @GetMapping("/findByVehiclePaged")
    public Page<TireModel> findByVehiclePaged(
            @RequestParam Long vehicleId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return tireService.findByVehicleModelId(vehicleId, pageable);
    }

    /**
     * Retrieves a paginated list of tires associated with a specific vehicle and status.
     *
     * @param vehicleId The ID of the vehicle for which to retrieve tires.
     * @param status    The status of the tires to filter.
     * @param page      The page number to retrieve (starting from 0).
     * @param size      The size of each page.
     * @return ResponseEntity containing a Page of TireModel objects associated with the specified vehicle and status.
     */
    @GetMapping("/findByVehicleAndStatus")
    public ResponseEntity<Page<TireModel>> findByVehicleModelIdAndStatus(
            @RequestParam Long vehicleId,
            @RequestParam Boolean status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<TireModel> tires = tireService.findByVehicleModelIdAndStatus(vehicleId, status, pageable);
        return new ResponseEntity<>(tires, HttpStatus.OK);
    }

    @GetMapping("/findByCompanyModelId")
    public ResponseEntity<Page<TireModel>> findByCompanyModelId(@RequestParam Long companyId,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<TireModel> tires = tireService.findByCompanyModelId(companyId, pageRequest);
        return new ResponseEntity<>(tires, HttpStatus.OK);
    }

    @GetMapping("/findByCompanyModelIdAndStatus")
    public ResponseEntity<List<TireModel>> findByCompanyModelIdAndStatus(
            @RequestParam Long companyId,
            @RequestParam Boolean status) {
        List<TireModel> tires = tireService.findByCompanyModelIdAndStatus(companyId, status);
        return new ResponseEntity<>(tires, HttpStatus.OK);
    }

    @GetMapping("/findByVehicleModelIdAndPositioningLocationCode")
    public ResponseEntity<Optional<TireModel>> findByVehicleModelIdAndId(
            @RequestParam Long vehicleId,
            @RequestParam Long positioning) {
        Optional<TireModel> tires = tireService.findByVehicleModelIdAndPositioningLocationCode(vehicleId, positioning);
        return new ResponseEntity<>(tires, HttpStatus.OK);
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

    @PutMapping("/changeTire/{id}")
    public ResponseEntity<TireModel> changeTire(@RequestBody TireModel tireModel, @PathVariable Long id) {
        TireModel updatedTire = tireService.changeTire(tireModel, id);
        return updatedTire != null ?
                new ResponseEntity<>(updatedTire, HttpStatus.OK) :
                ResponseEntity.notFound().build();
    }

    @PutMapping("/changeTire2")
    public ResponseEntity<TireModel> changeTire(
            @RequestParam Long id1,
            @RequestParam Long id2,
            @RequestParam String pos,
            @RequestParam Long v) {

        TireModel updatedTire = tireService.changeTire2(id1, id2, pos, v);

        if (updatedTire != null) {
            return new ResponseEntity<>(updatedTire, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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
