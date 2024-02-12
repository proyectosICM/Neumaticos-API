package com.icm.TireManagementApi.Requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTirePropertiesRequest {
    private Double temperature;
    private Double pressure;
    private Integer battery;
    private Long idvehicle;
    private Long idtire;

}
