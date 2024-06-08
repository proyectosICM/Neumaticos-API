package com.icm.tiremanagementapi.services;

import com.icm.tiremanagementapi.models.GasChangeModel;
import com.icm.tiremanagementapi.repositories.GasChangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class GasChangeService {
    @Autowired
    private GasChangeRepository gasChangeRepository;

    public List<GasChangeModel> getAll(){
        return gasChangeRepository.findAll();
    }

    public Optional<GasChangeModel> getById(Long id){
        return gasChangeRepository.findById(id);
    }

    public List<GasChangeModel> getByVehicleModelId(Long id) {
        return gasChangeRepository.findByVehicleModelId(id);
    }

    public List<GasChangeModel> getRecentByVehicleModelId(Long vehicleModelId) {
        Pageable pageable = PageRequest.of(0, 4);
        return gasChangeRepository.findRecentByVehicleModelId(vehicleModelId, pageable);
    }

    public List<GasChangeModel> getChangesByVehicleModelId(Long vehicleModelId) {
        Pageable pageable = PageRequest.of(0, 4);
        return gasChangeRepository.findRecentByVehicleModelId(vehicleModelId, pageable);
    }

    public Page<GasChangeModel> getByVehicleModelIdOrderByDesc(Long vehicleId, Pageable pageable) {
        return gasChangeRepository.findByVehicleModelIdOrderByDesc(vehicleId, pageable);
    }


    public GasChangeModel save(GasChangeModel gasChangeModel){
        return gasChangeRepository.save(gasChangeModel);
    }


}
