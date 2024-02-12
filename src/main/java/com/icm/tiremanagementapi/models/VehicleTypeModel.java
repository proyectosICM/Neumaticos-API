package com.icm.tiremanagementapi.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Defines the categories of vehicles recognized within the Tire Management System.
 * This entity classifies different types of vehicles, facilitating targeted management strategies
 * and maintenance routines specific to each vehicle category.
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "vehicletypes")
public class VehicleTypeModel {
    /**
     * Unique identifier for the vehicle type, automatically generated to ensure each type is distinct.
     */
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the vehicle type, such as 'Truck', 'Forklift', etc.,
     * which is critical for categorizing and managing vehicles accordingly.
     * The name is constrained to 50 characters to maintain data uniformity and integrity.
     */
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 50, message = "Name cannot exceed 50 characters or less")
    private String name;

    /**
     * Time stamp marking the creation of the vehicle type record, used for auditing and historical tracking.
     */
    @Column(name = "createdat", nullable = false, updatable = false)
    @CreationTimestamp
    private ZonedDateTime createdAt = ZonedDateTime.now(ZoneId.of("America/Lima"));

    /**
     * Timestamp of the last update of the vehicle type record,
     * which provides information about the latest modifications.
     */
    @Column(name = "updatedAt")
    @UpdateTimestamp
    private ZonedDateTime updatedAt = ZonedDateTime.now(ZoneId.of("America/Lima"));

}
