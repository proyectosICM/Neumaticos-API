package com.icm.tiremanagementapi.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.validation.constraints.Size;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Entity representation of a Company within the Tire Management System.
 * This class models the core attributes and behaviors of a company, including its identification,
 * name, and operational status. Companies are central to the application, serving as the primary
 * organizational unit under which vehicles, tires, and related transactions are grouped.
 * The status attribute determines the company's availability for new transactions,
 * where active companies can engage in the system's operations, and inactive ones are considered
 * dormant or archived. The creation and update timestamps provide audit trails for data governance.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "company")
public class CompanyModel {
    /**
     * Identifier code that auto-increments with the creation of a record.
     */
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the company used for identification and display purposes throughout the application
     */
    @Size(max = 255, message = "Name cannot exceed 255 characters")
    private String name;

    /**
     * Indicates if the company is active or inactive for display and filtering purposes.
     */
    private Boolean status;

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
