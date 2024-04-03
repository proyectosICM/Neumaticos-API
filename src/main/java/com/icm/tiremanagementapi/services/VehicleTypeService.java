package com.icm.tiremanagementapi.services;

import com.icm.tiremanagementapi.models.VehicleTypeModel;
import com.icm.tiremanagementapi.repositories.VehicleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleTypeService {
    @Autowired
    private VehicleTypeRepository vehicleTypeRepository;

    public Optional<VehicleTypeModel> findById(Long id) {
        return vehicleTypeRepository.findById(id);
    }

    public List<VehicleTypeModel> findAll() {
        return vehicleTypeRepository.findAll();
    }

    public VehicleTypeModel createVehicleType(VehicleTypeModel vehicleType) {
        return vehicleTypeRepository.save(vehicleType);
    }

    public VehicleTypeModel updateVehicleType(VehicleTypeModel vehicleType, Long id) {
        return vehicleTypeRepository.findById(id)
                .map(existingVehicleType -> {
                    existingVehicleType.setName(vehicleType.getName());
                    return vehicleTypeRepository.save(existingVehicleType);
                })
                .orElse(null);
    }

    public void deleteVehicleType(Long id) {
        vehicleTypeRepository.deleteById(id);
    }
}
