package com.icm.tiremanagementapi.controllers;

import com.icm.tiremanagementapi.models.IrregularitiesTireModel;
import com.icm.tiremanagementapi.Services.IrregularitiesTireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
/**
 * Controller class to manage operations related to IrregularitiesTire entities.
 * Provides endpoints for retrieving, creating, updating, and deleting irregularity records.
 */
@RestController
@RequestMapping("/api/irregularities")
public class IrregularitiesTireController {
    @Autowired
    private IrregularitiesTireService irregularitiesTireService;
    @GetMapping("/page")
    public ResponseEntity<Page<IrregularitiesTireModel>> getAllIrregularities(Pageable pageable) {
        Page<IrregularitiesTireModel> page = irregularitiesTireService.getAllIrregularities(pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/company/page/{companyId}")
    public ResponseEntity<Page<IrregularitiesTireModel>> getIrregularitiesByCompanyId(@PathVariable Long companyId,
                                                                                      @RequestParam(defaultValue = "0") int page,
                                                                                      @RequestParam(defaultValue = "6") int size) {
        Page<IrregularitiesTireModel> data = irregularitiesTireService.findIrregularitiesByCompanyId(companyId, page, size);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/company/{companyId}/vehicle/{vehicleId}/page")
    public ResponseEntity<Page<IrregularitiesTireModel>> getIrregularitiesByCompanyIdAndVehicleId(@PathVariable Long companyId, @PathVariable Long vehicleId, Pageable pageable) {
        Page<IrregularitiesTireModel> page = irregularitiesTireService.findIrregularitiesByCompanyIdAndVehicleId(companyId, vehicleId, pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }


    @GetMapping("/recent/{vehicleModelId}")
    public ResponseEntity<Page<IrregularitiesTireModel>> getRecentIrregularitiesByVehicleModelId(@PathVariable Long vehicleModelId) {
        Page<IrregularitiesTireModel> page = irregularitiesTireService.findRecentIrregularitiesByVehicleModelId(vehicleModelId);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IrregularitiesTireModel> getIrregularityById(@PathVariable Long id) {
        return irregularitiesTireService.getIrregularityById(id)
                .map(irregularity -> new ResponseEntity<>(irregularity, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<IrregularitiesTireModel> createIrregularity(@RequestBody IrregularitiesTireModel irregularity) {
        IrregularitiesTireModel createdIrregularity = irregularitiesTireService.createIrregularity(irregularity);
        return new ResponseEntity<>(createdIrregularity, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IrregularitiesTireModel> updateIrregularity(@PathVariable Long id, @RequestBody IrregularitiesTireModel irregularity) {
        IrregularitiesTireModel updatedIrregularity = irregularitiesTireService.updateIrregularity(irregularity, id);
        return updatedIrregularity != null ?
                new ResponseEntity<>(updatedIrregularity, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIrregularity(@PathVariable Long id) {
        irregularitiesTireService.deleteIrregularity(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
