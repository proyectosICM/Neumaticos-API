package com.icm.tiremanagementapi.controllers;

import com.icm.tiremanagementapi.models.GasChangeModel;
import com.icm.tiremanagementapi.services.GasChangeService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/gas-changes")
public class GasChangeController {
    @Autowired
    private GasChangeService gasChangeService;

    @GetMapping
    public List<?> getAll(){
        return gasChangeService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<?> getById(@PathVariable Long id){
        return gasChangeService.getById(id);
    }

    @GetMapping("/vehicle-model/{vehicleModelId}")
    public ResponseEntity<List<GasChangeModel>> getByVehicleModelId(@PathVariable Long vehicleModelId) {
        List<GasChangeModel> gasChanges = gasChangeService.getByVehicleModelId(vehicleModelId);
        return new ResponseEntity<>(gasChanges, HttpStatus.OK);
    }

    @GetMapping("/recent/{vehicleModelId}")
    public ResponseEntity<List<GasChangeModel>> getRecentByVehicleModelId(@PathVariable Long vehicleModelId) {
        List<GasChangeModel> gasChanges = gasChangeService.getRecentByVehicleModelId(vehicleModelId);
        return new ResponseEntity<>(gasChanges, HttpStatus.OK);
    }

    @GetMapping("/records-page/{vehicleModelId}")
    public ResponseEntity<Page<GasChangeModel>> getByVehicleModelIdOrderByDesc(
            @PathVariable Long vehicleModelId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<GasChangeModel> gasChanges = gasChangeService.getByVehicleModelIdOrderByDesc(vehicleModelId, pageable);
        return new ResponseEntity<>(gasChanges, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GasChangeModel> save(@RequestBody GasChangeModel gasChangeModel) {
        GasChangeModel savedGasChange = gasChangeService.save(gasChangeModel);
        return new ResponseEntity<>(savedGasChange, HttpStatus.CREATED);
    }

}
