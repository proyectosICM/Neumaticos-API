package com.icm.tiremanagementapi.dto.GasDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GasRecordMonthlyAveragesDTO {
    private String month;
    private Double avgPressure;
}
