package com.icm.tiremanagementapi.controllers;

import com.icm.tiremanagementapi.models.GasChangeModel;
import com.icm.tiremanagementapi.services.GasChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

    /**
     * Excels
     * */
    @GetMapping("/export/{vehicleId}")
    public ResponseEntity<byte[]> exportGasChanges(@PathVariable Long vehicleId) throws IOException {
        List<GasChangeModel> gasChanges = gasChangeService.getByVehicleModelId(vehicleId);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Gas Changes");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Día de instalación");
        headerRow.createCell(1).setCellValue("Hora de instalación");
        headerRow.createCell(2).setCellValue("Placa del vehiculo");

        // Fill data rows
        int rowNum = 1;
        for (GasChangeModel gasChange : gasChanges) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(gasChange.getChangeDate().toString());
            row.createCell(1).setCellValue(gasChange.getChangeTime().toString());
            row.createCell(2).setCellValue(gasChange.getVehicleModel().getPlaca());
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        workbook.close();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=gas_changes.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(baos.toByteArray());
    }

    @PostMapping
    public ResponseEntity<GasChangeModel> save(@RequestBody GasChangeModel gasChangeModel) {
        GasChangeModel savedGasChange = gasChangeService.save(gasChangeModel);
        return new ResponseEntity<>(savedGasChange, HttpStatus.CREATED);
    }

}
