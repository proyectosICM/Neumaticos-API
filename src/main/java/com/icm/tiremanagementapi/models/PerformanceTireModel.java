package com.icm.tiremanagementapi.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * This model is used to store the performance of tires over time and is utilized to display performance in charts.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "performance_tire")
public class PerformanceTireModel {
    /**
     * Identifier code that auto-increments with the creation of a record.
     */
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Tire temperature measured in degrees Celsius. This property is critical
     * for monitoring tire health and performance, especially under varying operational conditions.
     */
    @DecimalMin(value = "-60.0", inclusive = true, message = "Temperature must be greater than or equal to -60°C")
    @DecimalMax(value = "120.0", inclusive = true, message = "Temperature must be less than or equal to 120°C")
    private Double temperature;

    /**
     * Tire pressure measured in psi. Proper pressure levels are vital for tire longevity and vehicle safety.
     */
    @DecimalMin(value = "-20.0", inclusive = true, message = "Pressure must be greater than or equal to 0 psi")
    @DecimalMax(value = "180.0", inclusive = true, message = "Pressure must be less than or equal to 100 psi")
    private Double pressure;

    /**
     * Battery level of the sensor device mounted on the tire, expressed as a percentage.
     * Ensures continuous monitoring capability by indicating when battery replacement or charging is needed.
     */
    @DecimalMin(value = "0.0", inclusive = true, message = "Battery level must be greater than or equal to 0")
    @DecimalMax(value = "100.0", inclusive = true, message = "Battery level must be less than or equal to 100")
    private Double batteryLevel;

    /**
     * Links this performance to a specific vehicle within the system, enabling association between tires and vehicles.
     */
    @ManyToOne
    @JoinColumn(name = "vehicle", nullable = true)
    private VehicleModel vehicleModel;

    /**
     * Associates the performance with a company, reflecting ownership or responsibility for maintenance.
     */
    @ManyToOne
    @JoinColumn(name = "company", nullable = false)
    private CompanyModel company;

    /**
     * Reference to the tire experiencing the performance.
     * This direct association is crucial for pinpointing the specific tire involved in the performance.
     */
    @ManyToOne
    @JoinColumn(name = "tire", nullable = false)
    private TireModel tire;

    /**
     * Timestamps for recording the creation and last update times of the record.
     * - 'createdAt' is set at the time of creation and is not updatable.
     * - 'updatedAt' is set at the time of creation and updated on every modification to the record.
     */
    @Column(name = "createdAt", nullable = false, updatable = false)
    @CreationTimestamp
    private ZonedDateTime createdAt = ZonedDateTime.now(ZoneId.of("America/Lima"));

    @Column(name = "updatedAt")
    @UpdateTimestamp
    private ZonedDateTime updatedAt = ZonedDateTime.now(ZoneId.of("America/Lima"));
}
