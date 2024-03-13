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
 * This model is associated with tire irregularities and is used to add images and context
 * that can assist in resolving or provide proof for the resolution of the irregularity.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "irregularities_tire_images")
public class ImagesIrregularitiesTireModel {
    /**
     * Identifier code that auto-increments with the creation of a record.
     */
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of the image in the directory.
     */
    @Size(max = 50, message = "Name cannot exceed 50 characters")
    private String imageName;

    /**
     * Detail that provides an explanation of the problem or event depicted in the image
     */
    @Size(max = 550, message = "Name cannot exceed 550 characters")
    private String details;

    /**
     * Irregularity associated with the image
     */
    @ManyToOne
    @JoinColumn(name = "irregularities", nullable = true)
    private IrregularitiesTireModel irregularitiesTireModel;

    /**
     * Irregularity associated with the image
     */
    @ManyToOne
    @JoinColumn(name = "company", nullable = true)
    private CompanyModel companyModel;

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
