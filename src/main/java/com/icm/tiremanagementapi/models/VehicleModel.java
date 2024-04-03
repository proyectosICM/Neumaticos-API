package com.icm.tiremanagementapi.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "vehicle")
public class VehicleModel {
    /**
     * Unique identifier for the vehicle, auto-generated to ensure uniqueness within the database.
     */
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * License plate number of the vehicle.
     * It is optional and can be assigned based on specific vehicle types or operational needs.
     */
    private String placa;

    /**
     * Association to the company that owns or operates the vehicle,
     * establishing vehicle management and accountability.
     */
    @ManyToOne
    @JoinColumn(name = "company", nullable = false)
    private CompanyModel companyModel;

    /**
     * Categorizes the vehicle into a specific type, such as a truck, forklift, etc.,
     * influencing its operational use and maintenance schedules.
     */
    @ManyToOne
    @JoinColumn(name = "type", nullable = false)
    private VehicleTypeModel vehicleType;

    /**
     * Reflects the operational status of the vehicle, indicating whether it is active and in use or inactive.
     */
    private Boolean status;

    /**
     * Defined optimal temperature for tire performance, guiding maintenance checks and alerts.
     */
    private Double standardTemperature;

    /**
     * Defined optimal pressure for tire performance, critical for safety and efficiency in vehicle operations.
     */
    private Double standardPressure;

    /**
     * Timestamps for recording the creation and last update times of the record.
     * - 'createdAt' is set at the time of creation and is not updatable.
     * - 'updatedAt' is set at the time of creation and updated on every modification to the record.
     */
    @Column(name = "createdat", nullable = false, updatable = false)
    @CreationTimestamp
    private ZonedDateTime createdAt = ZonedDateTime.now(ZoneId.of("America/Lima"));

    @Column(name = "updatedAt")
    @UpdateTimestamp
    private ZonedDateTime updatedAt = ZonedDateTime.now(ZoneId.of("America/Lima"));

}
