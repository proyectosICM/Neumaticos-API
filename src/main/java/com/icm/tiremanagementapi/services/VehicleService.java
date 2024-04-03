package com.icm.tiremanagementapi.services;

import com.icm.tiremanagementapi.models.VehicleModel;
import com.icm.tiremanagementapi.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;

    public Optional<VehicleModel> findById(Long id) {
        return vehicleRepository.findById(id);
    }

    public List<VehicleModel> findAll(){
        return vehicleRepository.findAll();
    }

    public Page<VehicleModel> findAll(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            return vehicleRepository.findAll(pageable);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving vehicles", e);
        }
    }

    public ResponseEntity<VehicleModel> findByPlaca(String placa) {
        try {
            VehicleModel vehicle = vehicleRepository.findByPlaca(placa)
                    .orElseThrow(() -> new RuntimeException("Vehículo no encontrado con la placa: " + placa));
            return ResponseEntity.ok(vehicle);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    public Page<VehicleModel> findByCompanyModelId(Long companyId, int page, int size) {
        if (companyId != null) {
            Pageable pageable = PageRequest.of(page, size);
            return vehicleRepository.findByCompanyModelId(companyId, pageable);
        } else {
            return Page.empty();
        }
    }

    public Page<VehicleModel> findByCompanyModelIdAndStatus(Long companyId, Boolean status, int page, int size) {
        if (companyId != null) {
            Pageable pageable = PageRequest.of(page, size);
            return vehicleRepository.findByCompanyModelIdAndStatus(companyId, status, pageable);
        } else {
            return Page.empty();
        }
    }

    public Page<VehicleModel> findByVehicleTypeIdAndCompanyModelId(Long vehicleTypeId, Long companyId, int page, int size) {
        if (vehicleTypeId != null && companyId != null) {
            Pageable pageable = PageRequest.of(page, size);
            return vehicleRepository.findByVehicleTypeIdAndCompanyModelId(vehicleTypeId, companyId, pageable);
        } else {
            return Page.empty();
        }
    }

    public Page<VehicleModel> findByVehicleTypeIdAndStatusAndCompanyId(
            Long typeId, Boolean status, Long companyId, int page, int size) {
        if (typeId != null && companyId != null) {
            Pageable pageable = PageRequest.of(page, size);
            return vehicleRepository.findByVehicleTypeIdAndStatusAndCompanyModelId(typeId, status, companyId, pageable);
        } else {
            return Page.empty();
        }
    }

    public VehicleModel createVehicle(VehicleModel vehicle) {
        if (vehicleRepository.findByPlaca(vehicle.getPlaca()).isPresent()) {
            throw new RuntimeException("Ya existe un vehículo con la placa: " + vehicle.getPlaca());
        }
        return vehicleRepository.save(vehicle);
    }

    public VehicleModel updateVehicle(VehicleModel vehicle, Long id) {
        return vehicleRepository.findById(id)
                .map(existingVehicle -> {
                    existingVehicle.setPlaca(vehicle.getPlaca());
                    existingVehicle.setVehicleType(vehicle.getVehicleType());
                    existingVehicle.setCompanyModel(vehicle.getCompanyModel());
                    existingVehicle.setStandardTemperature(vehicle.getStandardTemperature());
                    existingVehicle.setStandardPressure(vehicle.getStandardPressure());
                    existingVehicle.setStatus(vehicle.getStatus());

                    return vehicleRepository.save(existingVehicle);
                })
                .orElse(null);
    }

    public void deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
    }
}
