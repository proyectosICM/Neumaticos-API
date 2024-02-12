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
     * Unique identifier for the company, serving as the primary key within the database.
     * This ID is automatically generated upon creation to ensure each company has a distinct identifier.
     * It is crucial for linking company-related data across the application.
     */
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The registered name of the company. It is used for identification and display purposes throughout the application.
     In addition to being used to filter data by company (tires, vehicles, workers, etc).
     * The name is restricted to a maximum of 255 characters to ensure compatibility with external systems and databases.
     */
    @Size(max = 255, message = "Name cannot exceed 255 characters")
    private String name;

    /**
     * Indicates whether the company is currently active or inactive.
     * This is used to determine when and how to display the company based on the filters applied
     */
    private Boolean status;

    /**
     * The date and time when this company was created.
     * This attribute serves auditing purposes by tracking the initial creation of the record.
     */
    @Column(name = "createdAt", nullable = false, updatable = false)
    @CreationTimestamp
    private ZonedDateTime createdAt = ZonedDateTime.now(ZoneId.of("America/Lima"));

    /**
     * The date and time when this record was last updated.
     * This attribute is used for auditing, allowing tracking of the most recent changes to the record.
     */
    @Column(name = "updatedAt")
    @UpdateTimestamp
    private ZonedDateTime updatedAt = ZonedDateTime.now(ZoneId.of("America/Lima"));
}
