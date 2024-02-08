package com.icm.TireManagementApi.Services;

import com.icm.TireManagementApi.Models.PositioningModel;
import com.icm.TireManagementApi.Repositories.PositioningRepository;
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

    /**
     * Retrieves all tire positions associated with a specific vehicle type.
     *
     * @param vehicleTypeId The ID of the vehicle type for which to retrieve tire positions.
     * @return A list of PositioningModel objects.
     */
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

    // Agrega aquí cualquier otro método de servicio que necesites
}