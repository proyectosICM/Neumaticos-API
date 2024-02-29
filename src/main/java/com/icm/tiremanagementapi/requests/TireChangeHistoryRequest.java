package com.icm.tiremanagementapi.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TireChangeHistoryRequest {
    private Long vehicleId;
    private Long companyId;
    private Integer type;
    private String cod1;
    private String cod2;
}
