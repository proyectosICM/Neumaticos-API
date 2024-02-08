package com.icm.TireManagementApi.Models;

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

/**
 * Represents a tire for a vehicle within the system.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tires")
public class TireModel {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Identification code cannot be blank")
    @Size(max = 10, message = "Identification code cannot exceed 10 characters")
    private String identificationCode;

    @DecimalMin(value = "-60.0", inclusive = true, message = "Temperature must be greater than or equal to -60°C")
    @DecimalMax(value = "120.0", inclusive = true, message = "Temperature must be less than or equal to 120°C")
    private Double temperature;

    @DecimalMin(value = "-20.0", inclusive = true, message = "Pressure must be greater than or equal to 0 psi")
    @DecimalMax(value = "180.0", inclusive = true, message = "Pressure must be less than or equal to 100 psi")
    private Double pressure;

    /**
     * Battery level of the device associated with the tire.
     */
    @DecimalMin(value = "0.0", inclusive = true, message = "Battery level must be greater than or equal to 0")
    @DecimalMax(value = "100.0", inclusive = true, message = "Battery level must be less than or equal to 100")
    private Double batteryLevel;

    /**
     * Role associated with the user to manage their access and options
     */
    @ManyToOne
    @JoinColumn(name = "vehicle", nullable = true)
    private VehicleModel vehicleModel;

    /**
     * Status of the tire (active/inactive).
     */
    private Boolean status;

    /**
     * The position of the tire on the vehicle, 0 for spare
     */
    private PositioningModel positioning;

    /**
     * Date and time this user was created.
     */
    @Column(name = "createdat", nullable = false, updatable = false)
    @CreationTimestamp
    private ZonedDateTime createdAt = ZonedDateTime.now(ZoneId.of("America/Lima"));

    /**
     * Date and time this user was updated.
     */
    @Column(name = "updatedAt")
    @UpdateTimestamp
    private ZonedDateTime updatedAt = ZonedDateTime.now(ZoneId.of("America/Lima"));

}
