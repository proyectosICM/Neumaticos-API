package com.icm.tiremanagementapi.controllers;

import com.icm.tiremanagementapi.models.IrregularitiesTireModel;
import com.icm.tiremanagementapi.services.IrregularitiesTireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/irregularities")
public class IrregularitiesTireController {
    @Autowired
    private IrregularitiesTireService irregularitiesTireService;

    @GetMapping("/{id}")
    public ResponseEntity<IrregularitiesTireModel> findById(@PathVariable Long id) {
        return irregularitiesTireService.findById(id)
                .map(irregularity -> new ResponseEntity<>(irregularity, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/companyAndVehicleList")
    public ResponseEntity<List<IrregularitiesTireModel>> findByCompanyIdAndVehicleModelId(
            @RequestParam Long companyId,
            @RequestParam Long vehicleId) {

        List<IrregularitiesTireModel> sortedIrregularities = irregularitiesTireService.findByCompanyIdAndVehicleModelId(companyId, vehicleId);

        if (sortedIrregularities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(sortedIrregularities, HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<IrregularitiesTireModel>> findAll(Pageable pageable) {
        Page<IrregularitiesTireModel> page = irregularitiesTireService.findAll(pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/company/page")
    public ResponseEntity<Page<IrregularitiesTireModel>> findByCompanyId(@RequestParam Long companyId,
                                                                                      @RequestParam(defaultValue = "0") int page,
                                                                                      @RequestParam(defaultValue = "6") int size) {
        Page<IrregularitiesTireModel> data = irregularitiesTireService.findByCompanyId(companyId, page, size);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/recent/{vehicleModelId}")
    public ResponseEntity<Page<IrregularitiesTireModel>> findByVehicleModelIdOrderByCreatedAtDesc(@PathVariable Long vehicleModelId) {
        Page<IrregularitiesTireModel> page = irregularitiesTireService.findByVehicleModelIdOrderByCreatedAtDesc(vehicleModelId);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/companyAndVehicle/page")
    public ResponseEntity<Page<IrregularitiesTireModel>> findByCompanyIdAndVehicleModelId(
            @RequestParam Long companyId,
            @RequestParam Long vehicleId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        Page<IrregularitiesTireModel> data = irregularitiesTireService.findByCompanyIdAndVehicleModelId(companyId, vehicleId, page, size);
        return new ResponseEntity<>(data, HttpStatus.OK);
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
