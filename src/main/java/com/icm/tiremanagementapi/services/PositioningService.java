package com.icm.tiremanagementapi.services;

import com.icm.tiremanagementapi.models.PositioningModel;
import com.icm.tiremanagementapi.repositories.PositioningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PositioningService {
    @Autowired
    private PositioningRepository positioningRepository;

    @Autowired
    public PositioningService(PositioningRepository positioningRepository) {
        this.positioningRepository = positioningRepository;
    }

    public List<PositioningModel> findAllPositionings() {
        return positioningRepository.findAll();
    }

    public List<PositioningModel> findPositioningsByVehicleTypeId(Long vehicleTypeId) {
        return positioningRepository.findByVehicleTypeId(vehicleTypeId);
    }

    public Optional<PositioningModel> findPositioningById(Long id) {
        return positioningRepository.findById(id);
    }

    public PositioningModel savePositioning(PositioningModel positioning) {
        return positioningRepository.save(positioning);
    }

    public void deletePositioning(Long id) {
        positioningRepository.deleteById(id);
    }
}