    package com.icm.TireManagementApi.Models;

    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;
    import org.hibernate.annotations.CreationTimestamp;
    import org.hibernate.annotations.UpdateTimestamp;

    import java.time.ZoneId;
    import java.time.ZonedDateTime;

    /**
     * Represents the positioning of a tire on a vehicle within the system.
     * This can include positions such as front-left, front-right, rear-left, rear-right, or spare.
     */
    @Entity
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Table(name = "positioning")
    public class PositioningModel {
        @Id
        @Column(unique = true, nullable = false)
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        /**
         * A code that specifies the tire's location on the vehicle.
         */
        private String locationCode;

        /**
         * Textual description of the tire's location.
         */
        private String description;

        /**
         * Indicates the side of the vehicle where the tire is located, such as 'Driver' or 'Passenger'.
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
         * The type of this vehicle.
         */
        @ManyToOne
        @JoinColumn(name = "type", nullable = false)
        private VehicleTypeModel vehicleType;

        /**
         * Date and time when this positioning record was created.
         */
        @Column(name = "createdat", nullable = false, updatable = false)
        @CreationTimestamp
        private ZonedDateTime createdAt;

        /**
         * Date and time when this positioning record was last updated.
         */
        @Column(name = "updatedAt")
        @UpdateTimestamp
        private ZonedDateTime updatedAt;
    }
