package com.icm.tiremanagementapi.services;

import com.icm.tiremanagementapi.models.*;
import com.icm.tiremanagementapi.repositories.IrregularitiesTireRepository;
import com.icm.tiremanagementapi.repositories.PositioningRepository;
import com.icm.tiremanagementapi.repositories.TireRepository;
import com.icm.tiremanagementapi.repositories.VehicleRepository;
import com.icm.tiremanagementapi.requests.CheckResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing operations related to Tire entities.
 * This service provides methods for retrieving, creating, updating, and deleting tire records in the system.
 */
@Service
public class TireService {
    @Autowired
    private TireRepository tireRepository;
    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private IrregularitiesTireRepository irregularitiesTireRepository;

    @Autowired
    private PositioningRepository positioningRepository;
    /**
     * Retrieves a list of all tires in the system.
     *
     * @return List of TireModel objects.
     */
    public List<TireModel> getAll() {
        return tireRepository.findAll();
    }

    /**
     * Retrieves a specific tire by its ID.
     *
     * @param id The ID of the tire to retrieve.
     * @return Optional containing the TireModel if found, otherwise empty.
     */
    public Optional<TireModel> getById(Long id) {
        return tireRepository.findById(id);
    }

    /**
     * Retrieves a list of all tires associated with a specific vehicle.
     * This method is designed to fetch a comprehensive list without pagination,
     * suitable for scenarios where the complete set of associated tires is required.
     *
     * @param vehicleId The ID of the vehicle for which to   retrieve the associated tires.
     * @return List of TireModel objects associated with the specified vehicle ID.
     */
    public List<TireModel> findTiresByVehicleId(TireStatus status) {
        return tireRepository.findByStatus(status);
    }

    /**
     * Retrieves a list of tires associated with a specific vehicle using pagination.
     *
     * @param vehicle  The vehicle for which to retrieve tires.
     * @param pageable The pageable information for pagination.
     * @return Page of TireModel objects associated with the specified vehicle.
     */
    public Page<TireModel> findByVehicleModelId(Long vehicle, Pageable pageable) {
        return tireRepository.findByVehicleModelId(vehicle, pageable);
    }

    /**
     * Retrieves a paginated list of tires associated with a specific vehicle and status.
     *
     * @param vehicleId The ID of the vehicle for which to retrieve tires.
     * @param status    The status of the tires to filter.
     * @param pageable  The pageable information for pagination.
     * @return Page of TireModel objects associated with the specified vehicle and status.
     */
    public Page<TireModel > findByVehicleModelIdAndStatus(Long vehicleId, Boolean status, Pageable pageable) {
        return tireRepository.findByVehicleModelIdAndStatus(vehicleId, status, pageable);
    }

    public List<TireModel> findByCompanyModelIdAndStatus(Long companyId, Boolean status) {
        return tireRepository.findByCompanyModelIdAndStatus(companyId, status);
    }

    public List<TireModel> findByVehicleModelIdAndPositioningLocationCode(Long vehicleId, String positioning) {
        return tireRepository.findByVehicleModelIdAndPositioningLocationCode(vehicleId, positioning);
    }

    /**
     * Creates a new tire record in the system.
     *
     * @param tire The TireModel object representing the new tire.
     * @return The created TireModel.
     */
    public TireModel createTire(TireModel tire) {
        return tireRepository.save(tire);
    }

    /**
     * Updates an existing tire record in the system.
     *
     * @param tire The updated TireModel.
     * @param id   The ID of the tire to update.
     * @return The updated TireModel if the tire with the given ID is found, otherwise null.
     */
    public TireModel updateTire(TireModel tire, Long id) {
        return tireRepository.findById(id)
                .map(existingTire -> {
                    existingTire.setCodname(tire.getCodname());
                    existingTire.setStatus(tire.getStatus());
                    return tireRepository.save(existingTire);
                })
                .orElse(null);
    }

    public TireModel changeTire(TireModel tire, Long id) {
        if (TireStatus.FREE.equals(tire.getStatus())) {
            return tireRepository.findById(id)
                    .map(existingTire -> {
                        existingTire.setStatus(TireStatus.FREE);
                        existingTire.setVehicleModel(null);
                        existingTire.setPositioning(null);
                        return tireRepository.save(existingTire);
                    })
                    .orElse(null);
        } else if (TireStatus.IN_USE.equals(tire.getStatus())) {
            return tireRepository.findById(id)
                    .map(existingTire -> {
                        existingTire.setStatus(TireStatus.IN_USE);
                        existingTire.setVehicleModel(tire.getVehicleModel());
                        existingTire.setPositioning(tire.getPositioning());
                        return tireRepository.save(existingTire);
                    })
                    .orElse(null);
        }
        return null; // Retorna null si no se cumple ninguna condición
    }
    public TireModel changeTire2(Long id1, Long id2, String pos, Long v) {
        // Primero, intentamos encontrar y actualizar el primer neumático.
        Optional<TireModel> result1 = tireRepository.findById(id1).map(
                n1 -> {
                    n1.setStatus(TireStatus.FREE);
                    n1.setVehicleModel(null);
                    n1.setPositioning(null);
                    return tireRepository.save(n1);
                });

        // Verificamos si el primer neumático fue actualizado con éxito.
        if (result1.isPresent()) {
            VehicleModel vehicle = new VehicleModel();
            vehicle.setId(v);

            PositioningModel position = new PositioningModel();

            PositioningModel positioningModel =  positioningRepository.findByLocationCode(pos);
            //position.setId(positioningModel.get().getId());
            // Si el primer neumático se actualizó con éxito, procedemos con el segundo.
            Optional<TireModel> result2 = tireRepository.findById(id2).map(n2 -> {
                n2.setStatus(TireStatus.IN_USE);
                n2.setVehicleModel(vehicle);
                n2.setPositioning(positioningModel);
                return tireRepository.save(n2);
            });

            // Devolvemos el segundo resultado si está presente, de lo contrario devolvemos null.
            return result2.orElse(null);
        } else {
            // Si el primer neumático no se pudo actualizar, devolvemos null o lanzamos una excepción según la lógica del negocio.
            return null;
        }
    }


    /**
     * Deletes a tire record from the system by its ID.
     *
     * @param id The ID of the tire to delete.
     */
    public void deleteTire(Long id) {
        tireRepository.deleteById(id);
    }

}
