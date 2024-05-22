package com.icm.tiremanagementapi.controllers;

import com.icm.tiremanagementapi.models.GasChangeModel;
import com.icm.tiremanagementapi.services.GasChangeService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping
    public ResponseEntity<?> save(@RequestBody GasChangeModel gasChangeModel){
        GasChangeModel data = gasChangeService.save(gasChangeModel);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
