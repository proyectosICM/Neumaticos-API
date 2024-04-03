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
@Table(name = "tires")
public class TireModel {
    /**
     * Unique identifier for the tire, auto-generated to ensure each tire has a distinct ID.
     */
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * codname for tire
     */
    private String codname;

    /**
     * Tire Status (FREE, IN_USE, DAMAGED)
     */
    @Enumerated(EnumType.STRING)
    private TireStatus status;

    /**
     * Position associated with the tire
     */
    @ManyToOne
    @JoinColumn(name = "positioning", nullable = true)
    private PositioningModel positioningModel;

    /**
     * Vehicle associated with the tire
     */
    @ManyToOne
    @JoinColumn(name = "vehicle", nullable = true)
    private VehicleModel vehicleModel;

    /**
     * Company associated with the tire
     */
    @ManyToOne
    @JoinColumn(name = "company", nullable = true)
    private CompanyModel companyModel;

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
