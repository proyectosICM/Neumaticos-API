package com.icm.tiremanagementapi.services;

import com.icm.tiremanagementapi.models.GasRecordModel;
import com.icm.tiremanagementapi.repositories.GasRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GasRecordService {
    @Autowired
    private GasRecordRepository gasRecordRepository;

    public List<GasRecordModel> getAll(){
        return gasRecordRepository.findAll();
    }

    public Optional<GasRecordModel> getById(Long id){
        return gasRecordRepository.findById(id);
    }

    public GasRecordModel save(GasRecordModel gasChangeModel){
        return gasRecordRepository.save(gasChangeModel);
    }

}
