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

    /**
     * This class serves as a detailed record of tire-specific issues, offering insights into anomalies that may affect vehicle safety and efficiency.
     * Irregularities are linked to specific tires and optionally to vehicles, providing a comprehensive view of incidents across the fleet.
     */
    @Entity
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Table(name = "irregularities_tire")
    public class IrregularitiesTireModel {
        /**
         * Unique identifier for the irregularity,
         * automatically generated to ensure distinct records within the database.
         */
        @Id
        @Column(unique = true, nullable = false)
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        /**
         * Descriptive name of the irregularity, summarizing the nature of the issue for quick reference.
         */
        private String nameIrregularity;


        /**
         * Detailed description of the irregularity. Provides specific information about the issue, its severity,
         * and any other relevant details.
         */
        @Nullable
        private String detailsIrregularity;

        /**
         * Reference to the vehicle associated with this irregularity, if applicable.
         * This association allows for tracking of irregularities by vehicle, enhancing the system's diagnostic capabilities.
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
         * Reference to the tire experiencing the irregularity.
         * This direct association is crucial for pinpointing the specific tire involved in the irregularity.
         */
        @ManyToOne
        @JoinColumn(name = "tire", nullable = false)
        private TireModel tire;

        /**
         * Records the temperature at which the irregularity was identified,
         * crucial for issues related to thermal performance.
         */
        private Boolean status;

        /**
         * Temperature at which the irregularity was recorded, relevant for temperature-specific issues.
         */
        private Double recordedTemperature;

        /**
         * Captures the pressure level of the tire at the time of the irregularity,
         * essential for understanding pressure-related issues.
         */
        private Double recordedPressure;

        /**
         * Logs the battery level of the monitoring device at the time the irregularity was recorded, relevant for device performance assessment.
         */
        private Double recordedBatteryLevel;

        /**
         * Timestamp denoting when the irregularity record was initially created,
         * serving as an audit trail for incident tracking.
         */
        @Column(name = "createdAt", nullable = false, updatable = false)
        @CreationTimestamp
        private ZonedDateTime createdAt = ZonedDateTime.now(ZoneId.of("America/Lima"));

        /**
         * Timestamp indicating the most recent update to the irregularity record, facilitating the monitoring of issue resolution progress.
         */
        @Column(name = "updatedAt")
        @UpdateTimestamp
        private ZonedDateTime updatedAt = ZonedDateTime.now(ZoneId.of("America/Lima"));
    }
