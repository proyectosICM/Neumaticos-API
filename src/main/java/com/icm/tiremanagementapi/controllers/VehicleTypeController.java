package com.icm.tiremanagementapi.controllers;

import com.icm.tiremanagementapi.models.VehicleTypeModel;
import com.icm.tiremanagementapi.services.VehicleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/vehicletypes")
public class VehicleTypeController {

    @Autowired
    private VehicleTypeService vehicleTypeService;

    @GetMapping("/{id}")
    public ResponseEntity<VehicleTypeModel> findById(@PathVariable Long id) {
        Optional<VehicleTypeModel> vehicleType = vehicleTypeService.findById(id);
        return vehicleType.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<VehicleTypeModel> findAll() {
        return vehicleTypeService.findAll();
    }

    @PostMapping
    public ResponseEntity<VehicleTypeModel> createVehicleType(@RequestBody VehicleTypeModel vehicleTypeModel) {
        VehicleTypeModel createdVehicleType = vehicleTypeService.createVehicleType(vehicleTypeModel);
        return new ResponseEntity<>(createdVehicleType, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleTypeModel> updateVehicleType(@RequestBody VehicleTypeModel vehicleTypeModel, @PathVariable Long id) {
        VehicleTypeModel updatedVehicleType = vehicleTypeService.updateVehicleType(vehicleTypeModel, id);
        return updatedVehicleType != null ?
                new ResponseEntity<>(updatedVehicleType, HttpStatus.OK) :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicleType(@PathVariable Long id) {
        vehicleTypeService.deleteVehicleType(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
