package com.icm.tiremanagementapi.controllers;

import com.icm.tiremanagementapi.models.PerformanceTireModel;
import com.icm.tiremanagementapi.requests.performanceTire.DailyAverageDTO;
import com.icm.tiremanagementapi.requests.performanceTire.HourlyAverageDTO;
import com.icm.tiremanagementapi.requests.performanceTire.MonthlyAverageDTO;
import com.icm.tiremanagementapi.requests.performanceTire.YearlyAverageDTO;
import com.icm.tiremanagementapi.services.PerformanceTireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/performance-tire")
public class PerformanceTireController {
    @Autowired
    private PerformanceTireService performanceTireService;

    @GetMapping("/hourly-averages")
    public ResponseEntity<List<HourlyAverageDTO>> findHourlyAveragesBySensorIdAndDate(
            @RequestParam Long tireId,
            @RequestParam int year,
            @RequestParam int month,
            @RequestParam int day) {
        List<HourlyAverageDTO> hourlyAverages = performanceTireService.findHourlyAveragesBySensorIdAndDate(tireId, year, month, day);
        if (hourlyAverages.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(hourlyAverages);
    }

    @GetMapping("/daily-averages")
    public ResponseEntity<List<DailyAverageDTO>> findDailyAveragesBySensorIdAndMonth(@RequestParam Long tireId, @RequestParam int year, @RequestParam int month) {
        List<DailyAverageDTO> dailyAverages = performanceTireService.findDailyAveragesBySensorIdAndMonth(tireId, year, month);
        if (dailyAverages.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dailyAverages);
    }

    @GetMapping("/monthly-averages")
    public ResponseEntity<List<MonthlyAverageDTO>> findMonthlyAveragesBySensorIdAndYear(@RequestParam Long tireId, @RequestParam int year) {
        List<MonthlyAverageDTO> monthlyAverages = performanceTireService.findMonthlyAveragesBySensorIdAndYear(tireId, year);
        return ResponseEntity.ok(monthlyAverages);
    }

    @GetMapping("/yearly-averages")
    public ResponseEntity<List<YearlyAverageDTO>> findYearlyAveragesBySensorId(@RequestParam Long tireId) {
        List<YearlyAverageDTO> yearlyAverages = performanceTireService.findYearlyAveragesBySensorId(tireId);
        if (yearlyAverages.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(yearlyAverages);
    }

    @PostMapping
    public ResponseEntity<PerformanceTireModel> createPerformanceTire(@RequestBody PerformanceTireModel performanceTire) {
        PerformanceTireModel savedPerformanceTire = performanceTireService.createPerformanceTire(performanceTire);
        return new ResponseEntity<>(savedPerformanceTire, HttpStatus.CREATED);
    }
}
