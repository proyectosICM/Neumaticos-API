package com.icm.tiremanagementapi.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "gas_records")
public class GasRecordModel {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalTime hour;

    @Column(nullable = false)
    private LocalDate day;

    @Column(nullable = false)
    private Double pressure;

    @ManyToOne
    @JoinColumn(name = "vehicle", referencedColumnName = "id", nullable = false)
    private VehicleModel vehicleModel;
}
