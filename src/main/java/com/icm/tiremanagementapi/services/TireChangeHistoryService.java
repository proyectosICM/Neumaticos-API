package com.icm.tiremanagementapi.services;

import com.icm.tiremanagementapi.models.TireChangeHistoryModel;
import com.icm.tiremanagementapi.repositories.TireChangeHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TireChangeHistoryService {
    @Autowired
    private TireChangeHistoryRepository tireChangeHistoryRepository;

    public Optional<TireChangeHistoryModel> findById(Long id) {
        return tireChangeHistoryRepository.findById(id);
    }

    public List<TireChangeHistoryModel> findAll() {
        return tireChangeHistoryRepository.findAll();
    }

    public TireChangeHistoryModel save(Long vehicleId, Long companyId, Integer type, String cod1, String cod2) {
        TireChangeHistoryModel data = new TireChangeHistoryModel();

        data.getVehicleModel().setId(vehicleId);
        data.getCompanyModel().setId(companyId);

        String changeTypeDetail = type == 1 ? "neumático" : "sensor";
        String details = String.format("Se ha realizado un cambio de %s del %s %s al %s", changeTypeDetail, changeTypeDetail, cod1, cod2);
        data.setDetails(details);

        return tireChangeHistoryRepository.save(data);
    }

    public TireChangeHistoryModel edit(Long id, TireChangeHistoryModel tireChangeHistoryModel){
        return tireChangeHistoryRepository.findById(id)
                .map(existing->{
                    existing.setDetails(tireChangeHistoryModel.getDetails());
                    existing.setCompanyModel(tireChangeHistoryModel.getCompanyModel());
                    existing.setVehicleModel(tireChangeHistoryModel.getVehicleModel());
                    return tireChangeHistoryRepository.save(existing);
                })
                .orElse(null);
    }

    public void delete(Long id){
        tireChangeHistoryRepository.deleteById(id);
    }
}
