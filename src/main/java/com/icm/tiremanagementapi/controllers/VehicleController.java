package com.icm.tiremanagementapi.controllers;

import com.icm.tiremanagementapi.models.VehicleModel;
import com.icm.tiremanagementapi.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/vehicle")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/{id}")
    public ResponseEntity<VehicleModel> findById(@PathVariable Long id) {
        Optional<VehicleModel> vehicle = vehicleService.findById(id);
        return vehicle.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<VehicleModel> findAll(){
        return vehicleService.findAll();
    }

    @GetMapping("/page")
    public ResponseEntity<Page<VehicleModel>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<VehicleModel> vehicles = vehicleService.findAll(page, size);
        return new ResponseEntity<>(vehicles, HttpStatus.OK);
    }

    @GetMapping("/findByPlaca")
    public ResponseEntity<?> findByPlaca(@RequestParam String placa) {
        return vehicleService.findByPlaca(placa);
    }


    @GetMapping("/findByCompany")
    public Page<VehicleModel> findByCompanyModelId(
            @RequestParam Long companyId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size) {
        return vehicleService.findByCompanyModelId(companyId, page, size);
    }

    @GetMapping("/findByCompanyAndStatus")
    public Page<VehicleModel> findByCompanyModelIdAndStatus(
            @RequestParam Long companyId,
            @RequestParam Boolean status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return vehicleService.findByCompanyModelIdAndStatus(companyId, status, page, size);
    }

    @GetMapping("/findByVehicleTypeAndCompany")
    public ResponseEntity<Page<VehicleModel>> findByVehicleTypeIdAndCompanyId(
            @RequestParam Long vehicleTypeId,
            @RequestParam Long companyId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<VehicleModel> vehicles = vehicleService.findByVehicleTypeIdAndCompanyModelId(vehicleTypeId, companyId, page, size);
        return new ResponseEntity<>(vehicles, HttpStatus.OK);
    }

    @GetMapping("/findByTypeAndStatusAndCompany")
    public Page<VehicleModel> findByTypeAndStatusAndCompany(
            @RequestParam Long typeId,
            @RequestParam Boolean status,
            @RequestParam Long companyId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return vehicleService.findByVehicleTypeIdAndStatusAndCompanyId(typeId, status, companyId, page, size);
    }

    @PostMapping
    public ResponseEntity<VehicleModel> createVehicle(@RequestBody VehicleModel vehicleModel) {
        VehicleModel createdVehicle = vehicleService.createVehicle(vehicleModel);
        return new ResponseEntity<>(createdVehicle, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleModel> updateVehicle(@RequestBody VehicleModel vehicleModel, @PathVariable Long id) {
        VehicleModel updatedVehicle = vehicleService.updateVehicle(vehicleModel, id);
        return updatedVehicle != null ?
                new ResponseEntity<>(updatedVehicle, HttpStatus.OK) :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
