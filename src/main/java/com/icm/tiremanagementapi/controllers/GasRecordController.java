package com.icm.tiremanagementapi.controllers;

import com.icm.tiremanagementapi.dto.GasDTO.GasRecordDailyAveragesDTO;
import com.icm.tiremanagementapi.dto.GasDTO.GasRecordHourlyAverageDTO;
import com.icm.tiremanagementapi.dto.GasDTO.GasRecordMonthlyAveragesDTO;
import com.icm.tiremanagementapi.dto.GasDTO.GasRecordYearlyAveragesDTO;
import com.icm.tiremanagementapi.models.GasChangeModel;
import com.icm.tiremanagementapi.models.GasRecordModel;
import com.icm.tiremanagementapi.services.GasRecordService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
    public List<GasRecordHourlyAverageDTO> getHourlyAverages(
            @RequestParam Long vehicleId) {
        LocalDate currentDateInPeru = LocalDate.now(ZoneId.of("America/Lima"));
        return gasRecordService.getHourlyAveragesByVehicleIdAndDay(vehicleId, currentDateInPeru);
    }

    @GetMapping("/daily-averages")
    public List<GasRecordDailyAveragesDTO> getDailyAverages(@RequestParam Long vehicleId) {
        LocalDate currentDateInPeru = LocalDate.now(ZoneId.of("America/Lima"));
        int currentYear = currentDateInPeru.getYear();
        int currentMonth = currentDateInPeru.getMonthValue();

        return gasRecordService.getDailyAveragesByVehicleIdAndMonth(vehicleId, currentYear, currentMonth);
    }

    @GetMapping("/monthly-averages")
    public List<GasRecordMonthlyAveragesDTO> getMonthlyAverages(@RequestParam Long vehicleId) {
        // Obtener el año actual en la zona horaria de Perú
        int currentYear = LocalDate.now(ZoneId.of("America/Lima")).getYear();
        return gasRecordService.getMonthlyAveragesByVehicleIdAndYear(vehicleId, currentYear);
    }


    @GetMapping("/yearly-averages")
    public List<GasRecordYearlyAveragesDTO> getYearlyAverages(
            @RequestParam Long vehicleId) {
        return gasRecordService.getYearlyAveragesByVehicleId(vehicleId);
    }

    /**
     * Excels
     * */
    @GetMapping("/export/{vehicleId}")
    public ResponseEntity<byte[]> exportGasRecords(@PathVariable Long vehicleId) throws IOException {
        List<GasRecordModel> gasRecords = gasRecordService.getByVehicleModelId(vehicleId);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Gas Records");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Día de registro");
        headerRow.createCell(1).setCellValue("Hora de registro");
        headerRow.createCell(2).setCellValue("Presión");
        headerRow.createCell(3).setCellValue("Placa del vehiculo");

        // Fill data rows
        int rowNum = 1;
        for (GasRecordModel gasRecord : gasRecords) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(gasRecord.getDay().toString());
            row.createCell(1).setCellValue(gasRecord.getHour().toString());
            row.createCell(2).setCellValue(gasRecord.getPressure());
            row.createCell(3).setCellValue(gasRecord.getVehicleModel().getPlaca());
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        workbook.close();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=gas_records.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(baos.toByteArray());
    }

}
