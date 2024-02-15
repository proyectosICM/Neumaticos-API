package com.icm.tiremanagementapi.controllers;

import com.icm.tiremanagementapi.requests.performanceTire.DailyAverageDTO;
import com.icm.tiremanagementapi.requests.performanceTire.HourlyAverageDTO;
import com.icm.tiremanagementapi.requests.performanceTire.MonthlyAverageDTO;
import com.icm.tiremanagementapi.requests.performanceTire.YearlyAverageDTO;
import com.icm.tiremanagementapi.services.PerformanceTireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    /**
     * Endpoint to retrieve hourly averages of temperature, pressure, and battery level for a specific tire on a given scheduled meeting.
     * @param tireId The ID of the tire.
     * @param year The year of the scheduled meeting.
     * @param month The month of the scheduled meeting.
     * @param day The day of the scheduled meeting.
     * @return ResponseEntity containing a list of DTOs with the hour and average values of temperature, pressure, and battery level.
     */
        @GetMapping("/hourly-averages")
    public ResponseEntity<List<HourlyAverageDTO>> getHourlyAveragesByTireIdAndDate(
            @RequestParam Long tireId,
            @RequestParam int year,
            @RequestParam int month,
            @RequestParam int day) {
        List<HourlyAverageDTO> hourlyAverages = performanceTireService.findHourlyAveragesByTireIdAndDate(tireId, year, month, day);
        if (hourlyAverages.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(hourlyAverages);
    }

    @GetMapping("/daily-averages")
    public ResponseEntity<List<DailyAverageDTO>> getDailyAveragesByTireIdAndMonth(@RequestParam Long tireId, @RequestParam int year, @RequestParam int month) {
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
    @GetMapping("/monthly-averages")
    public ResponseEntity<List<MonthlyAverageDTO>> getMonthlyAveragesByTireIdAndYear(@RequestParam Long tireId, @RequestParam int year) {
        List<MonthlyAverageDTO> monthlyAverages = performanceTireService.findMonthlyAveragesByTireIdAndYear(tireId, year);
        return ResponseEntity.ok(monthlyAverages);
    }

    /**
     * Endpoint to retrieve yearly averages of temperature, pressure, and battery level for a specific tire.
     * @param tireId The ID of the tire for which averages are being calculated.
     * @return ResponseEntity containing a list of YearlyAverageDTOs with the year and average values.
     */
    @GetMapping("/yearly-averages")
    public ResponseEntity<List<YearlyAverageDTO>> getYearlyAveragesByTireId(@RequestParam Long tireId) {
        List<YearlyAverageDTO> yearlyAverages = performanceTireService.findYearlyAveragesByTireId(tireId);
        if (yearlyAverages.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(yearlyAverages);
    }
}
