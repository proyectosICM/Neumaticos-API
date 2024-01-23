package com.icm.TireManagementApi.Repositories;

import com.icm.TireManagementApi.Models.TireModel;
import com.icm.TireManagementApi.Models.VehicleModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing operations related to Tire entities.
 * This repository provides methods for retrieving, creating, updating, and deleting tire records in the system.
 */
@Repository
public interface TireRepository extends JpaRepository<TireModel, Long> {

    /**
     * Retrieves a specific tire by its identification code.
     *
     * @param code The identification code of the tire to retrieve.
     * @return Optional containing the TireModel if found, otherwise empty.
     */
    Optional<TireModel> findByIdentificationCode(String code);

    /**
     * Finds all tires associated with a specific vehicle.
     *
     * @param vehicle The vehicle for which to retrieve tires.
     * @return List of TireModel objects associated with the specified vehicle.
     */
    List<TireModel> findByVehicleModel(VehicleModel vehicle);

    /**
     * Finds all tires associated with a specific vehicle using pagination.
     *
     * @param vehicle  The vehicle for which to retrieve tires.
     * @param pageable The pageable information for pagination.
     * @return Page of TireModel objects associated with the specified vehicle.
     */
    Page<TireModel> findByVehicleModel(VehicleModel vehicle, Pageable pageable);

    /**
     * Finds all tires associated with a specific vehicle and status.
     *
     * @param vehicle The vehicle for which to retrieve tires.
     * @param status  The status of the tires to retrieve.
     * @return List of TireModel objects associated with the specified vehicle and status.
     */
    List<TireModel> findByVehicleModelAndStatus(VehicleModel vehicle, Boolean status);

    /**
     * Finds all tires associated with a specific vehicle and status using pagination.
     *
     * @param vehicle  The vehicle for which to retrieve tires.
     * @param status   The status of the tires to retrieve.
     * @param pageable The pageable information for pagination.
     * @return Page of TireModel objects associated with the specified vehicle and status.
     */
    Page<TireModel> findByVehicleModelAndStatus(VehicleModel vehicle, Boolean status, Pageable pageable);
}
