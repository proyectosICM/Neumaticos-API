package com.icm.tiremanagementapi.requests.performanceTire;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyAverageDTO {
    private int day;
    private int month;
    private double avgTemperature;
    private double avgPressure;
    private double avgBatteryLevel;
}
