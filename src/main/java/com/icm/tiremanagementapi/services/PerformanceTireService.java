package com.icm.tiremanagementapi.services;

import com.icm.tiremanagementapi.models.PerformanceTireModel;
import com.icm.tiremanagementapi.repositories.PerformanceTireRepository;
import com.icm.tiremanagementapi.requests.performanceTire.DailyAverageDTO;
import com.icm.tiremanagementapi.requests.performanceTire.HourlyAverageDTO;
import com.icm.tiremanagementapi.requests.performanceTire.MonthlyAverageDTO;
import com.icm.tiremanagementapi.requests.performanceTire.YearlyAverageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PerformanceTireService {
    @Autowired
    PerformanceTireRepository performanceTireRepository;

    public List<PerformanceTireModel> getAllPerformanceTires(){
        return performanceTireRepository.findAll();
    }

    public Optional<PerformanceTireModel>getPerformanceTiresById(Long id){
        return performanceTireRepository.findById(id);
    }

    /**
     * Retrieves hourly averages of temperature, pressure, and battery level for a specific tire on a given scheduled meeting.
     *
     * @param tireId The ID of the tire.
     * @param year The year of the scheduled meeting.
     * @param month The month of the scheduled meeting.
     * @param day The day of the scheduled meeting.
     * @return A list of DTOs containing the hour and average values of temperature, pressure, and battery level.
     */
    public List<HourlyAverageDTO> findHourlyAveragesByTireIdAndDate(Long tireId, int year, int month, int day) {
        List<Object[]> hourlyAverages = performanceTireRepository.findHourlyAveragesByTireIdAndDate(tireId, year, month, day);
        return hourlyAverages.stream().map(objects -> new HourlyAverageDTO(
                (Integer) objects[0], // hour
                (Double) objects[1], // avgTemperature
                (Double) objects[2], // avgPressure
                (Double) objects[3]  // avgBatteryLevel
        )).collect(Collectors.toList());
    }

    public List<DailyAverageDTO> findDailyAveragesByTireIdAndMonth(Long tireId, int year, int month) {
        List<Object[]> dailyAverages = performanceTireRepository.findDailyAveragesByTireIdAndMonth(tireId, year, month);
        return dailyAverages.stream().map(objects ->  new DailyAverageDTO(
                (Integer) objects[0], // month
                (Integer) objects[1], // day
                (Double) objects[2], // avgTemperature
                (Double) objects[3], // avgPressure
                (Double) objects[4]  // avgBatteryLevel
        )).collect(Collectors.toList());
    }

    /**
     * Retrieves monthly averages of temperature, pressure, and battery level for a specific tire in a given year.
     *
     * @param tireId The ID of the tire.
     * @param year The year for which averages are calculated.
     * @return A list of DTOs containing the month and the average values of temperature, pressure, and battery level.
     */
    public List<MonthlyAverageDTO> findMonthlyAveragesByTireIdAndYear(Long tireId, int year) {
        List<Object[]> monthlyAverages = performanceTireRepository.findMonthlyAveragesByTireIdAndYear(tireId, year);
        return monthlyAverages.stream().map(objects -> new MonthlyAverageDTO(
                (Integer) objects[0], // month
                (Double) objects[1], // avgTemperature
                (Double) objects[2], // avgPressure
                (Double) objects[3]  // avgBatteryLevel
        )).collect(Collectors.toList());
    }

    /**
     * Retrieves yearly averages of temperature, pressure, and battery level for a specific tire.
     *
     * @param tireId The ID of the tire for which averages are being calculated.
     * @return A list of YearlyAverageDTO objects containing the year and the average values of temperature, pressure, and battery level.
     */
    public List<YearlyAverageDTO> findYearlyAveragesByTireId(Long tireId) {
        List<Object[]> yearlyAverages = performanceTireRepository.findYearlyAveragesByTireId(tireId);
        return yearlyAverages.stream().map(objects -> new YearlyAverageDTO(
                (Integer) objects[0], // year
                (Double) objects[1], // avgTemperature
                (Double) objects[2], // avgPressure
                (Double) objects[3]  // avgBatteryLevel
        )).collect(Collectors.toList());
    }

}
