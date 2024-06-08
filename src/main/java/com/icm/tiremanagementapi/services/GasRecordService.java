package com.icm.tiremanagementapi.services;

import com.icm.tiremanagementapi.models.GasRecordModel;
import com.icm.tiremanagementapi.repositories.GasRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class GasRecordService {
    @Autowired
    private GasRecordRepository gasRecordRepository;

    public List<GasRecordModel> getAll(){
        return gasRecordRepository.findAll();
    }

    public Optional<GasRecordModel> getById(Long id){
        return gasRecordRepository.findById(id);
    }

    public List<GasRecordModel> getByVehicleModelId(Long id) {
        return gasRecordRepository.findByVehicleModelId(id);
    }

    public Page<GasRecordModel> getByVehicleModelId(Long id, Pageable pageable) {
        return gasRecordRepository.findByVehicleModelIdOrderByDayDescHourDesc(id, pageable);
    }

    public GasRecordModel save(GasRecordModel gasChangeModel){
        return gasRecordRepository.save(gasChangeModel);
    }

    /**
     * Methods for statistics
     */
    public List<Object[]> getHourlyAveragesByVehicleIdAndDay(Long vehicleId, LocalDate day) {
        return gasRecordRepository.findHourlyAveragesByVehicleIdAndDay(vehicleId, day);
    }

    public List<Object[]> getDailyAveragesByVehicleIdAndMonth(Long vehicleId, int year, int month) {
        return gasRecordRepository.findDailyAveragesByVehicleIdAndMonth(vehicleId, year, month);
    }

    public List<Object[]> getMonthlyAveragesByVehicleIdAndYear(Long vehicleId, int year) {
        return gasRecordRepository.findMonthlyAveragesByVehicleIdAndYear(vehicleId, year);
    }

    public List<Object[]> getYearlyAveragesByVehicleId(Long vehicleId) {
        return gasRecordRepository.findYearlyAveragesByVehicleId(vehicleId);
    }

}
