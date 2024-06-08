package com.icm.tiremanagementapi.controllers;

import com.icm.tiremanagementapi.models.GasChangeModel;
import com.icm.tiremanagementapi.models.GasRecordModel;
import com.icm.tiremanagementapi.services.GasRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
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

    @GetMapping("/vehicle/{vehicleModelId}")
    public ResponseEntity<List<GasRecordModel>> getByVehicleModelId(@PathVariable Long vehicleModelId) {
        List<GasRecordModel> gasChanges = gasRecordService.getByVehicleModelId(vehicleModelId);
        return new ResponseEntity<>(gasChanges, HttpStatus.OK);
    }

    @GetMapping("/vehicle-page/{vehicleModelId}")
    public ResponseEntity<Page<GasRecordModel>> getByVehicleModelId(@PathVariable Long vehicleModelId,
                                                                    @RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "10") int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<GasRecordModel> gasChanges = gasRecordService.getByVehicleModelId(vehicleModelId, pageable);
        return new ResponseEntity<>(gasChanges, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody GasRecordModel gasRecordModel){
        GasRecordModel data = gasRecordService.save(gasRecordModel);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    /**
     * Methods for statistics
     */


    @GetMapping("/hourly-averages")
    public List<Object[]> getHourlyAverages(
            @RequestParam Long vehicleId) {
        // Obtener la fecha actual en la zona horaria de Perú
        LocalDate currentDateInPeru = LocalDate.now(ZoneId.of("America/Lima"));
        return gasRecordService.getHourlyAveragesByVehicleIdAndDay(vehicleId, currentDateInPeru);
    }

    @GetMapping("/daily-averages")
    public List<Object[]> getDailyAverages(
            @RequestParam Long vehicleId,
            @RequestParam int year,
            @RequestParam int month) {
        return gasRecordService.getDailyAveragesByVehicleIdAndMonth(vehicleId, year, month);
    }

    @GetMapping("/monthly-averages")
    public List<Object[]> getMonthlyAverages(
            @RequestParam Long vehicleId,
            @RequestParam int year) {
        return gasRecordService.getMonthlyAveragesByVehicleIdAndYear(vehicleId, year);
    }

    @GetMapping("/yearly-averages")
    public List<Object[]> getYearlyAverages(
            @RequestParam Long vehicleId) {
        return gasRecordService.getYearlyAveragesByVehicleId(vehicleId);
    }

}
