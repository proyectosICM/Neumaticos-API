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

/**
 * Represents a tire for a vehicle within the system.
 * Encapsulates data related to individual tires, including their identification, status,
 * and physical properties such as temperature and pressure. This class plays a key role
 * in monitoring tire conditions and managing alerts for anomalies.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tires")
public class TireModel {
    /**
     * Unique identifier for the tire, auto-generated to ensure each tire has a distinct ID.
     */
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codname;

    private Boolean status;

    /**
     * Links this tire to a specific vehicle within the system, enabling association between tires and vehicles.
     */
    @ManyToOne
    @JoinColumn(name = "vehicle", nullable = true)
    private VehicleModel vehicleModel;

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
