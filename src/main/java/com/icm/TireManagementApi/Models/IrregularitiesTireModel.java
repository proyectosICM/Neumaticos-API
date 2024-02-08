package com.icm.TireManagementApi.Models;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Represents an irregularity event for a tire on a vehicle within the system.
 * This entity tracks the specific issues or conditions that deviate from the normal or expected performance or status of a tire.
 */
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
     * The name or type of the irregularity. Provides a quick reference to the nature of the issue.
     */
    private String nameIrregularity;

    /**
     * Detailed description of the irregularity. Provides specific information about the issue, its severity, and any other relevant details.
     */
    @Nullable
    private String detailsIrregularity;

    /**
     * Vehicle associated with this irregularity. It may be null if the irregularity is not directly linked to a specific vehicle.
     */
    @ManyToOne
    @JoinColumn(name = "vehicle", nullable = true)
    private VehicleModel vehicleModel;

    /**
     * Company owning the vehicle. This field is mandatory as every irregularity must be associated with a specific company.
     */
    @ManyToOne
    @JoinColumn(name = "company", nullable = false)
    private CompanyModel company;

    /**
     * Indicates whether the irregularity is currently active (true) or has been resolved (false).
     */
    private Boolean status;

    /**
     * Timestamp marking the creation of this irregularity record. Automatically set to the current time at creation.
     */
    @Column(name = "createdAt", nullable = false, updatable = false)
    @CreationTimestamp
    private ZonedDateTime createdAt = ZonedDateTime.now(ZoneId.of("America/Lima"));

    /**
     * Timestamp of the last update made to this irregularity record. Automatically updated to the current time when modifications are made.
     */
    @Column(name = "updatedAt")
    @UpdateTimestamp
    private ZonedDateTime updatedAt = ZonedDateTime.now(ZoneId.of("America/Lima"));
}
