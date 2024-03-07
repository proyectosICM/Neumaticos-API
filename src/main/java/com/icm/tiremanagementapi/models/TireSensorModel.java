package com.icm.tiremanagementapi.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tires_sensor")
public class TireSensorModel {
    /**
     * Unique identifier for the tire, auto-generated to ensure each tire has a distinct ID.
     */
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * A unique code to identify the tire, facilitating easy tracking and management.
     */
    @NotBlank(message = "Identification code cannot be blank")
    @Size(max = 10, message = "Identification code cannot exceed 10 characters")
    private String identificationCode;

    /**
     * Current temperature of the tire, measured in degrees Celsius. This property is critical
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
     * Links this tire to a specific vehicle within the system, enabling association between tires and vehicles.
     */
    @ManyToOne
    @JoinColumn(name = "vehicle", nullable = true)
    private VehicleModel vehicleModel;

    /**
     * Associates the tire with a company, reflecting ownership or responsibility for maintenance.
     */
    @ManyToOne
    @JoinColumn(name = "company", nullable = false)
    private CompanyModel company;

    /**
     * Status of the tire (active/inactive).
     */
    private Boolean status;

    /**
     * Describes the positioning of the tire on the vehicle,
     * aiding in specific tire management and maintenance tasks.
     */
    @ManyToOne
    @JoinColumn(name = "positioning", nullable = true)
    private PositioningModel positioning;



    /**
     * Records the creation timestamp of the tire record, providing audit capabilities.
     */
    @Column(name = "createdat", nullable = false, updatable = false)
    @CreationTimestamp
    private ZonedDateTime createdAt = ZonedDateTime.now(ZoneId.of("America/Lima"));

    /**
     * Records the last update timestamp of the tire record, facilitating tracking of changes over time.
     */
    @Column(name = "updatedAt")
    @UpdateTimestamp
    private ZonedDateTime updatedAt = ZonedDateTime.now(ZoneId.of("America/Lima"));
}
