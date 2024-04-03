package com.icm.tiremanagementapi.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "positioning")
public class PositioningModel {
    /**
     * Identifier code that auto-increments with the creation of a record.
     */
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Unique code representing the tire's specific location on the vehicle,
     * aiding in precise identification and tracking.
     */
    private String locationCode;

    /**
     * Descriptive text detailing the tire's exact location, providing an intuitive understanding of its placement.
     */
    private String description;

    /**
     * Designates the side of the vehicle where the tire is mounted,
     * assisting in spatial orientation and maintenance tasks.
     */
    private String sideOfVehicle;

    /**
     * Specifies the axle of the vehicle on which the tire is located, such as 'Front', 'Middle', 'Rear'.
     */
    private String axle;

    /**
     * Position of the tire on the specified axle, for vehicles with dual tires on an axle, such as 'Inner' or 'Outer'.
     */
    private String positionOnAxle;

    /**
     * Associates the positioning with a specific type of vehicle, linking tire location to vehicle configuration.
     */
    @ManyToOne
    @JoinColumn(name = "type", nullable = false)
    private VehicleTypeModel vehicleType;

    /**
     * Timestamps for recording the creation and last update times of the record.
     * - 'createdAt' is set at the time of creation and is not updatable.
     * - 'updatedAt' is set at the time of creation and updated on every modification to the record.
     */
    @Column(name = "createdat", nullable = false, updatable = false)
    @CreationTimestamp
    private ZonedDateTime createdAt;

    @Column(name = "updatedAt")
    @UpdateTimestamp
    private ZonedDateTime updatedAt;
}