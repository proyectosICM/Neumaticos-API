package com.icm.tiremanagementapi.controllers;

import com.icm.tiremanagementapi.requests.performanceTire.DailyAverageDTO;
import com.icm.tiremanagementapi.requests.performanceTire.MonthlyAverageDTO;
import com.icm.tiremanagementapi.services.PerformanceTireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Handles HTTP requests related to tire performance.
 * Supports operations such as listing, retrieving, creating, updating, and deleting performance.
 */
@RestController
@RequestMapping("api/performance-tire")
public class PerformanceTireController {
    @Autowired
    private PerformanceTireService performanceTireService;

    @GetMapping("/daily-averages/{tireId}/{year}/{month}/")
    public ResponseEntity<List<DailyAverageDTO>> getDailyAveragesByTireIdAndMonth(@PathVariable Long tireId, @PathVariable int year, @PathVariable int month) {
        List<DailyAverageDTO> dailyAverages = performanceTireService.findDailyAveragesByTireIdAndMonth(tireId, year, month);
        if (dailyAverages.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dailyAverages);
    }

    /**
     * Endpoint to retrieve monthly averages of temperature, pressure, and battery level for a specific tire in a given year.
     * @param tireId The ID of the tire.
     * @param year The year for which averages are calculated.
     * @return ResponseEntity containing a list of DTOs with the month and average values.
     */
    @GetMapping("/monthly-averages/{tireId}/{year}")
    public ResponseEntity<List<MonthlyAverageDTO>> getMonthlyAveragesByTireIdAndYear(@PathVariable Long tireId, @PathVariable int year) {
        List<MonthlyAverageDTO> monthlyAverages = performanceTireService.findMonthlyAveragesByTireIdAndYear(tireId, year);
        return ResponseEntity.ok(monthlyAverages);
    }
}
