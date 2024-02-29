package com.icm.tiremanagementapi.controllers;

import com.icm.tiremanagementapi.models.TireChangeHistoryModel;
import com.icm.tiremanagementapi.services.TireChangeHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tire_change_history")
public class TireChangeHistoryController {
    @Autowired
    private TireChangeHistoryService tireChangeHistoryService;

    @GetMapping
    public ResponseEntity<List<TireChangeHistoryModel>> getAllTireChangeHistories() {
        return new ResponseEntity<>(tireChangeHistoryService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TireChangeHistoryModel> getTireChangeHistoryById(@PathVariable Long id) {
        return tireChangeHistoryService.getById(id)
                .map(tireChangeHistory -> new ResponseEntity<>(tireChangeHistory, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

/*
    @PostMapping("/save")
    public ResponseEntity<TireChangeHistoryModel> saveTireChangeHistory(@RequestBody TireChangeHistoryRequest request) {
        TireChangeHistoryModel savedRecord = tireChangeHistoryService.save(
                request.getVehicleId(),
                request.getCompanyId(),
                request.getType(),
                request.getCod1(),
                request.getCod2()
        );

        if (savedRecord != null) {
            return new ResponseEntity<>(savedRecord, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
*/

    @PutMapping("/{id}")
    public ResponseEntity<TireChangeHistoryModel> updateTireChangeHistory(@PathVariable Long id, @RequestBody TireChangeHistoryModel tireChangeHistoryModel) {
        TireChangeHistoryModel updatedTireChangeHistory = tireChangeHistoryService.edit(id, tireChangeHistoryModel);
        if (updatedTireChangeHistory != null) {
            return new ResponseEntity<>(updatedTireChangeHistory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTireChangeHistory(@PathVariable Long id) {
        tireChangeHistoryService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
