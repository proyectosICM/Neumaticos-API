package com.icm.TireManagementApi.Controllers;

import com.icm.TireManagementApi.Models.PositioningModel;
import com.icm.TireManagementApi.Services.PositioningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/positioning")
public class PositioningController {

    @Autowired
    private  PositioningService positioningService;

    @GetMapping
    public ResponseEntity<List<PositioningModel>> getAllPositionings() {
        List<PositioningModel> positionings = positioningService.findAllPositionings();
        return new ResponseEntity<>(positionings, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PositioningModel> getPositioningById(@PathVariable Long id) {
        return positioningService.findPositioningById(id)
                .map(positioning -> new ResponseEntity<>(positioning, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Endpoint to retrieve tire positions by vehicle type ID.
     *
     * @param vehicleTypeId The ID of the vehicle type.
     * @return ResponseEntity containing a list of PositioningModel objects.
     */
    @GetMapping("/vehicleType/{vehicleTypeId}")
    public ResponseEntity<List<PositioningModel>> getPositioningsByVehicleTypeId(@PathVariable Long vehicleTypeId) {
        List<PositioningModel> positionings = positioningService.findPositioningsByVehicleTypeId(vehicleTypeId);
        if (positionings.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(positionings);
    }

    @PostMapping
    public ResponseEntity<PositioningModel> createPositioning(@RequestBody PositioningModel positioning) {
        PositioningModel newPositioning = positioningService.savePositioning(positioning);
        return new ResponseEntity<>(newPositioning, HttpStatus.CREATED);
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePositioning(@PathVariable Long id) {
        positioningService.deletePositioning(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Puedes agregar más endpoints según sea necesario
}