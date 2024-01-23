package com.icm.TireManagementApi.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Represents a vehicle within the system.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "vehicle")
public class VehicleModel {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * License plate of the vehicle (can be null, assigned based on the vehicle type).
     */
    @Null(message = "License plate can be null, assigned based on the vehicle type")
    private String placa;

    /**
     * The company to which this vehicle belongs.
     */
    @ManyToOne
    @JoinColumn(name = "company", nullable = false)
    private CompanyModel company;

    /**
     * The type of this vehicle.
     */
    @ManyToOne
    @JoinColumn(name = "type", nullable = false)
    private VehicleTypeModel vehicleTypeModel;

    /**
     * The status of this vehicle (active/inactive).
     */
    private Boolean status;

    /**
     * Date and time when this vehicle was created.
     */
    @Column(name = "createdat", nullable = false, updatable = false)
    @CreationTimestamp
    private ZonedDateTime createdAt = ZonedDateTime.now(ZoneId.of("America/Lima"));

    /**
     * Date and time when this vehicle was last updated.
     */
    @Column(name = "updatedAt")
    @UpdateTimestamp
    private ZonedDateTime updatedAt = ZonedDateTime.now(ZoneId.of("America/Lima"));

}
