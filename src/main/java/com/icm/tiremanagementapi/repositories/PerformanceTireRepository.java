package com.icm.tiremanagementapi.repositories;

import com.icm.tiremanagementapi.models.PerformanceTireModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PerformanceTireRepository extends JpaRepository<PerformanceTireModel, Long> {
    @Query("SELECT HOUR(p.createdAt) as hour, AVG(p.temperature) as avgTemperature, AVG(p.pressure) as avgPressure, AVG(p.batteryLevel) as avgBatteryLevel " +
            "FROM PerformanceTireModel p " +
            "WHERE p.tire.id = :tireId AND YEAR(p.createdAt) = :year AND MONTH(p.createdAt) = :month AND DAY(p.createdAt) = :day " +
            "GROUP BY HOUR(p.createdAt) " +
            "ORDER BY HOUR(p.createdAt)")
    List<Object[]> findHourlyAveragesByTireIdAndDate(@Param("tireId") Long tireId, @Param("year") int year, @Param("month") int month, @Param("day") int day);

    @Query("SELECT DAY(p.createdAt) as day, MONTH(p.createdAt) as month, AVG(p.temperature) as avgTemperature, AVG(p.pressure) as avgPressure, AVG(p.batteryLevel) as avgBatteryLevel " +
            "FROM PerformanceTireModel p " +
            "WHERE p.tire.id = :tireId AND YEAR(p.createdAt) = :year AND MONTH(p.createdAt) = :month " +
            "GROUP BY DAY(p.createdAt) " +
            "ORDER BY DAY(p.createdAt)")
    List<Object[]> findDailyAveragesByTireIdAndMonth(@Param("tireId") Long tireId, @Param("year") int year, @Param("month") int month);

    /**
     * Retrieves monthly averages of temperature, pressure, and battery level for a specific tire in a given year.
     *
     * @param tireId The ID of the tire.
     * @param year The year for which averages are calculated.
     * @return A list of objects containing the month and the average values of temperature, pressure, and battery level.
     */
   @Query("SELECT MONTH(p.createdAt) as month, AVG(p.temperature) as avgTemperature, AVG(p.pressure) as avgPressure, AVG(p.batteryLevel) as avgBatteryLevel " +
            "FROM PerformanceTireModel p " +
            "WHERE p.tire.id = :tireId AND YEAR(p.createdAt) = :year " +
            "GROUP BY MONTH(p.createdAt) " +
            "ORDER BY MONTH(p.createdAt)")
    List<Object[]> findMonthlyAveragesByTireIdAndYear(@Param("tireId") Long tireId, @Param("year") int year);

    @Query("SELECT YEAR(p.createdAt) AS year, AVG(p.temperature) AS avgTemperature, AVG(p.pressure) AS avgPressure, AVG(p.batteryLevel) AS avgBatteryLevel " +
            "FROM PerformanceTireModel p " +
            "WHERE p.tire.id = :tireId " +
            "GROUP BY YEAR(p.createdAt) " +
            "ORDER BY YEAR(p.createdAt)")
    List<Object[]> findYearlyAveragesByTireId(@Param("tireId") Long tireId);


}