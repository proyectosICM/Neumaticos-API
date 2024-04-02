package com.icm.tiremanagementapi.services;

import com.icm.tiremanagementapi.repositories.IrregularitiesTireRepository;
import com.icm.tiremanagementapi.repositories.PositioningRepository;
import com.icm.tiremanagementapi.repositories.TireRepository;
import com.icm.tiremanagementapi.repositories.VehicleRepository;
import com.icm.tiremanagementapi.models.PositioningModel;
import com.icm.tiremanagementapi.models.TireModel;
import com.icm.tiremanagementapi.models.TireStatus;
import com.icm.tiremanagementapi.models.VehicleModel;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public Optional<TireModel> findByCodnameAndCompanyModelIdAndStatus(String codname, Long companyModelId) {
        return tireRepository.findByCodnameAndCompanyModelIdAndStatus(codname, companyModelId, TireStatus.FREE);
    }

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

    public Optional<TireModel> findByVehicleModelIdAndPositioningLocationCode(Long vehicleId, Long positioning) {
        return tireRepository.findByVehicleModelIdAndPositioningModelId(vehicleId, positioning);
    }

    public Page<TireModel> findByCompanyModelId(Long companyId, Pageable pageable) {
        return tireRepository.findByCompanyModelId(companyId, pageable);
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
        // Verificar si el neumático a actualizar existe
        TireModel existingTire = tireRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Neumático no encontrado con id: " + id));

        // Verificar si hay un neumático anterior que deba ser liberado
        if (tire.getVehicleModel() != null && tire.getPositioningModel() != null) {
            Optional<TireModel> oldTire = tireRepository.findByVehicleModelIdAndPositioningModelId(tire.getVehicleModel().getId(), tire.getPositioningModel().getId());
            if (oldTire.isPresent()) {
                // Liberar el neumático anterior
                TireModel tireToFree = oldTire.get();
                tireToFree.setPositioningModel(null);
                tireToFree.setVehicleModel(null);
                tireToFree.setStatus(TireStatus.FREE);
                tireRepository.save(tireToFree);
            }
        }

        // Actualizar el neumático existente con los nuevos detalles
        existingTire.setCodname(tire.getCodname());
        existingTire.setStatus(tire.getStatus());
        existingTire.setPositioningModel(tire.getPositioningModel());
        existingTire.setVehicleModel(tire.getStatus() == TireStatus.FREE ? null : tire.getVehicleModel());
        existingTire.setCompanyModel(tire.getCompanyModel());

        return tireRepository.save(existingTire);
    }

    public TireModel changeTire(TireModel tire, Long id) {
        if (TireStatus.FREE.equals(tire.getStatus())) {
            return tireRepository.findById(id)
                    .map(existingTire -> {
                        existingTire.setStatus(TireStatus.FREE);
                        existingTire.setVehicleModel(null);
                        existingTire.setPositioningModel(null);
                        return tireRepository.save(existingTire);
                    })
                    .orElse(null);
        } else if (TireStatus.IN_USE.equals(tire.getStatus())) {
            return tireRepository.findById(id)
                    .map(existingTire -> {
                        existingTire.setStatus(TireStatus.IN_USE);
                        existingTire.setVehicleModel(tire.getVehicleModel());
                        existingTire.setPositioningModel(tire.getPositioningModel());
                        return tireRepository.save(existingTire);
                    })
                    .orElse(null);
        }
        return null; // Retorna null si no se cumple ninguna condición
    }
    public TireModel changeTire2(Long id1, Long id2, String pos, Long v) {
        if(id1 != null && id2 != null) {
            // Primero, intentamos encontrar y actualizar el primer neumático.
            TireModel firstTire = tireRepository.findById(id1).orElse(null);

            if (firstTire != null) {
                firstTire.setStatus(TireStatus.FREE);
                firstTire.setVehicleModel(null);
                firstTire.setPositioningModel(null);
                tireRepository.save(firstTire);

                // Verificar si el primer neumático se actualizó a FREE correctamente.
                if (firstTire.getStatus() == TireStatus.FREE) {
                    VehicleModel vehicle = new VehicleModel();
                    vehicle.setId(v);

                    PositioningModel positioningModel = positioningRepository.findByLocationCode(pos);
                    if (positioningModel == null) {
                        // Manejar el caso en que la posición no se encuentra.
                        return null;
                    }

                    // Procedemos con el segundo neumático si el primer neumático fue actualizado correctamente.
                    return tireRepository.findById(id2).map(n2 -> {
                        n2.setStatus(TireStatus.IN_USE);
                        n2.setVehicleModel(vehicle);
                        n2.setPositioningModel(positioningModel);
                        return tireRepository.save(n2);
                    }).orElse(null);
                }
            }
            // Devolvemos null si el primer neumático no se encuentra o no se actualiza correctamente.

        }
        return null;
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
