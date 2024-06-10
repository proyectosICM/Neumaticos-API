package com.icm.tiremanagementapi.services;

import com.icm.tiremanagementapi.dto.GasDTO.GasRecordDailyAveragesDTO;
import com.icm.tiremanagementapi.dto.GasDTO.GasRecordHourlyAverageDTO;
import com.icm.tiremanagementapi.dto.GasDTO.GasRecordMonthlyAveragesDTO;
import com.icm.tiremanagementapi.dto.GasDTO.GasRecordYearlyAveragesDTO;
import com.icm.tiremanagementapi.models.GasRecordModel;
import com.icm.tiremanagementapi.repositories.GasRecordRepository;
import com.icm.tiremanagementapi.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<GasRecordHourlyAverageDTO> getHourlyAveragesByVehicleIdAndDay(Long vehicleId, LocalDate day) {
        List<Object[]> results = gasRecordRepository.findHourlyAveragesByVehicleIdAndDay(vehicleId, day);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        return results.stream()
                .map(result -> new GasRecordHourlyAverageDTO(result[0].toString() + ":00", (Double) result[1]))
                .collect(Collectors.toList());
    }

    public List<GasRecordDailyAveragesDTO> getDailyAveragesByVehicleIdAndMonth(Long vehicleId, int year, int month) {
        List<Object[]> results = gasRecordRepository.findDailyAveragesByVehicleIdAndMonth(vehicleId, year, month);
        return results.stream()
                .map(result -> new GasRecordDailyAveragesDTO(result[0].toString(), (Double) result[1]))
                .collect(Collectors.toList());
    }

    public List<GasRecordMonthlyAveragesDTO> getMonthlyAveragesByVehicleIdAndYear(Long vehicleId, int year) {
        List<Object[]> results = gasRecordRepository.findMonthlyAveragesByVehicleIdAndYear(vehicleId, year);
        return results.stream()
                .map(result -> new GasRecordMonthlyAveragesDTO(
                        DateUtils.getMonthName((Integer) result[0]),
                        (Double) result[1]
                ))
                .collect(Collectors.toList());
    }

    public List<GasRecordYearlyAveragesDTO> getYearlyAveragesByVehicleId(Long vehicleId) {
        List<Object[]> results = gasRecordRepository.findYearlyAveragesByVehicleId(vehicleId);
        return results.stream()
                .map(result -> new GasRecordYearlyAveragesDTO(
                        result[0].toString(),
                        (Double) result[1]
                ))
                .collect(Collectors.toList());
    }

}
