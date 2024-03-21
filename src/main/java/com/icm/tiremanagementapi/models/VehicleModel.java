package com.icm.tiremanagementapi.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Represents a vehicle within the system.
 * It encapsulates detailed information about vehicles, including their identification, associated company,
 * vehicle type, and operational status. This class also defines standard temperature and pressure settings
 * for tires, aiding in monitoring and maintenance routines.
 */
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
     * Timestamp that marks the creation of the vehicle record, used for auditing and historical tracking.
     */
    @Column(name = "createdat", nullable = false, updatable = false)
    @CreationTimestamp
    private ZonedDateTime createdAt = ZonedDateTime.now(ZoneId.of("America/Lima"));

    /**
     * Timestamp of the last update of the vehicle registration,
     * which provides information about the latest modifications.
     */
    @Column(name = "updatedAt")
    @UpdateTimestamp
    private ZonedDateTime updatedAt = ZonedDateTime.now(ZoneId.of("America/Lima"));

}
