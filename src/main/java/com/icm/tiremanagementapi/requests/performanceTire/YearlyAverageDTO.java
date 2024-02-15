package com.icm.tiremanagementapi.requests.performanceTire;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YearlyAverageDTO {
    private int year;
    private double avgTemperature;
    private double avgPressure;
    private double avgBatteryLevel;
}