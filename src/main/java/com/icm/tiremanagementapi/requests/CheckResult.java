package com.icm.tiremanagementapi.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckResult {
    private boolean shouldCreateIrregularity;
    private String irregularityName;
    private String irregularityDetail;
    private Double recordedTemperature;
    private Double recordedPressure;
    private Double recordedBatteryLevel;
    private Long idtire;
}
