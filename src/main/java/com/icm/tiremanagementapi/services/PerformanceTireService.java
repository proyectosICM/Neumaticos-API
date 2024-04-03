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

    public List<HourlyAverageDTO> findHourlyAveragesBySensorIdAndDate(Long tireId, int year, int month, int day) {
        List<Object[]> hourlyAverages = performanceTireRepository.findHourlyAveragesBySensorIdAndDate(tireId, year, month, day);
        return hourlyAverages.stream().map(objects -> new HourlyAverageDTO(
                (Integer) objects[0], // hour
                (Double) objects[1], // avgTemperature
                (Double) objects[2], // avgPressure
                (Double) objects[3]  // avgBatteryLevel
        )).collect(Collectors.toList());
    }

    public List<DailyAverageDTO> findDailyAveragesBySensorIdAndMonth(Long tireId, int year, int month) {
        List<Object[]> dailyAverages = performanceTireRepository.findDailyAveragesBySensorIdAndMonth(tireId, year, month);
        return dailyAverages.stream().map(objects ->  new DailyAverageDTO(
                (Integer) objects[0], // month
                (Integer) objects[1], // day
                (Double) objects[2], // avgTemperature
                (Double) objects[3], // avgPressure
                (Double) objects[4]  // avgBatteryLevel
        )).collect(Collectors.toList());
    }

    public List<MonthlyAverageDTO> findMonthlyAveragesBySensorIdAndYear(Long tireId, int year) {
        List<Object[]> monthlyAverages = performanceTireRepository.findMonthlyAveragesBySensorIdAndYear(tireId, year);
        return monthlyAverages.stream().map(objects -> new MonthlyAverageDTO(
                (Integer) objects[0], // month
                (Double) objects[1], // avgTemperature
                (Double) objects[2], // avgPressure
                (Double) objects[3]  // avgBatteryLevel
        )).collect(Collectors.toList());
    }

    public List<YearlyAverageDTO> findYearlyAveragesBySensorId(Long tireId) {
        List<Object[]> yearlyAverages = performanceTireRepository.findYearlyAveragesBySensorId(tireId);
        return yearlyAverages.stream().map(objects -> new YearlyAverageDTO(
                (Integer) objects[0], // year
                (Double) objects[1], // avgTemperature
                (Double) objects[2], // avgPressure
                (Double) objects[3]  // avgBatteryLevel
        )).collect(Collectors.toList());
    }

    public PerformanceTireModel createPerformanceTire(PerformanceTireModel performanceTire) {
        return performanceTireRepository.save(performanceTire);
    }
}
