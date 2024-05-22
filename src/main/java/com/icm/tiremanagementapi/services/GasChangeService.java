package com.icm.tiremanagementapi.services;

import com.icm.tiremanagementapi.models.GasChangeModel;
import com.icm.tiremanagementapi.repositories.GasChangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public GasChangeModel save(GasChangeModel gasChangeModel){
        return gasChangeRepository.save(gasChangeModel);
    }
}
