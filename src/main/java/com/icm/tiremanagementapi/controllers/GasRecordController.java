package com.icm.tiremanagementapi.controllers;

import com.icm.tiremanagementapi.models.GasRecordModel;
import com.icm.tiremanagementapi.services.GasRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/gas-records")
public class GasRecordController {
    @Autowired
    private GasRecordService gasRecordService;

    @GetMapping
    public List<GasRecordModel> getAll(){
        return gasRecordService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<GasRecordModel> getById(@PathVariable Long id){
        return gasRecordService.getById(id);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody GasRecordModel gasRecordModel){
        GasRecordModel data = gasRecordService.save(gasRecordModel);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
