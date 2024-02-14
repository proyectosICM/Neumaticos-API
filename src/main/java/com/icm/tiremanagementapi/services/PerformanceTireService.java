package com.icm.tiremanagementapi.services;

import com.icm.tiremanagementapi.models.PerformanceTireModel;
import com.icm.tiremanagementapi.repositories.PerformanceTireRepository;
import com.icm.tiremanagementapi.requests.performanceTire.DailyAverageDTO;
import com.icm.tiremanagementapi.requests.performanceTire.MonthlyAverageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<DailyAverageDTO> findDailyAveragesByTireIdAndMonth(Long tireId, int year, int month) {
        List<Object[]> results = performanceTireRepository.findDailyAveragesByTireIdAndMonth(tireId, year, month);
        List<DailyAverageDTO> dailyAverages = new ArrayList<>();
        for (Object[] result : results) {
            int resultMonth = (Integer) result[0];
            int day = (Integer) result[1];
            double avgTemperature = (Double) result[2];
            double avgPressure = (Double) result[3];
            double avgBatteryLevel = (Double) result[4];
            dailyAverages.add(new DailyAverageDTO(resultMonth, day, avgTemperature, avgPressure, avgBatteryLevel));
        }
        return dailyAverages;
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

}
