package com.icm.tiremanagementapi.models;

import jakarta.annotation.Nullable;
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
@Table(name = "irregularities_tire")
public class IrregularitiesTireModel {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Descriptive name of the irregularity, summarizing the nature of the issue for quick reference.
     */
    private String nameIrregularity;

    /**
     * Description of the irregularity, including issue details and severity.
     */
    @Nullable
    private String detailsIrregularity;

    /**
     * Vehicle associated with this irregularity, enabling tracking and diagnostics by vehicle.
     */
    @ManyToOne
    @JoinColumn(name = "vehicle", nullable = true)
    private VehicleModel vehicleModel;

    @ManyToOne
    @JoinColumn(name = "company", nullable = false)
    private CompanyModel company;

    @ManyToOne
    @JoinColumn(name = "sensor", nullable = false)
    private TireSensorModel tireSensorModel;

    @ManyToOne
    @JoinColumn(name = "tire", nullable = true)
    private TireModel tireModel;

    private Boolean status;

    private Double recordedTemperature;

    private Double recordedPressure;

    private Double recordedBatteryLevel;

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
