package com.icm.tiremanagementapi.repositories;

import com.icm.tiremanagementapi.models.GasRecordModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface GasRecordRepository extends JpaRepository<GasRecordModel, Long> {
    List<GasRecordModel> findByVehicleModelId(Long id);
    Page<GasRecordModel> findByVehicleModelIdOrderByDayDescHourDesc(Long id, Pageable pageable);

    /**
     * Methods for statistics
     */
    @Query("SELECT HOUR(g.hour) as hour, AVG(g.pressure) as avgPressure " +
            "FROM GasRecordModel g " +
            "WHERE g.vehicleModel.id = :vehicleId AND g.day = :day " +
            "GROUP BY HOUR(g.hour) " +
            "ORDER BY HOUR(g.hour)")
    List<Object[]> findHourlyAveragesByVehicleIdAndDay(@Param("vehicleId") Long vehicleId, @Param("day") LocalDate day);

    @Query("SELECT g.day as day, AVG(g.pressure) as avgPressure " +
            "FROM GasRecordModel g " +
            "WHERE g.vehicleModel.id = :vehicleId AND YEAR(g.day) = :year AND MONTH(g.day) = :month " +
            "GROUP BY g.day " +
            "ORDER BY g.day")
    List<Object[]> findDailyAveragesByVehicleIdAndMonth(@Param("vehicleId") Long vehicleId, @Param("year") int year, @Param("month") int month);

    @Query("SELECT MONTH(g.day) as month, AVG(g.pressure) as avgPressure " +
            "FROM GasRecordModel g " +
            "WHERE g.vehicleModel.id = :vehicleId AND YEAR(g.day) = :year " +
            "GROUP BY MONTH(g.day) " +
            "ORDER BY MONTH(g.day)")
    List<Object[]> findMonthlyAveragesByVehicleIdAndYear(@Param("vehicleId") Long vehicleId, @Param("year") int year);

    @Query("SELECT YEAR(g.day) as year, AVG(g.pressure) as avgPressure " +
            "FROM GasRecordModel g " +
            "WHERE g.vehicleModel.id = :vehicleId " +
            "GROUP BY YEAR(g.day) " +
            "ORDER BY YEAR(g.day)")
    List<Object[]> findYearlyAveragesByVehicleId(@Param("vehicleId") Long vehicleId);
}
