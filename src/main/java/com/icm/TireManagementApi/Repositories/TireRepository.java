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
     * Finds all tires associated with a specific vehicle using pagination.
     *
     * @param vehicle  The vehicle for which to retrieve tires.
     * @param pageable The pageable information for pagination.
     * @return Page of TireModel objects associated with the specified vehicle.
     */
    Page<TireModel> findByVehicleModelId(Long vehicle, Pageable pageable);

    /**
     * Retrieves a list of all tires associated with a specific vehicle.
     * This method is designed to fetch a comprehensive list without pagination,
     * suitable for scenarios where the complete set of associated tires is required.
     *
     * @param vehicle The ID of the vehicle for which to retrieve the associated tires.
     * @return List of TireModel objects associated with the specified vehicle ID.
     */
    List<TireModel> findByVehicleModelId(Long vehicle);


    /**
     * Finds all tires associated with a specific vehicle and status using pagination.
     *
     * @param vehicle  The vehicle for which to retrieve tires.
     * @param status   The status of the tires to retrieve.
     * @param pageable The pageable information for pagination.
     * @return Page of TireModel objects associated with the specified vehicle and status.
     */
    Page<TireModel> findByVehicleModelIdAndStatus(Long vehicle, Boolean status, Pageable pageable);

    /**
     * Finds all tires related to a specific vehicle and positioned at a specified location code without pagination.
     * Useful for retrieving tires based on their physical location on a vehicle.
     * @param vehicle The ID of the vehicle for which to find tires.
     * @param positioning A string representing the positioning code of the tire on the vehicle.
     * @return A list of TireModel objects that are associated with the given vehicle and have the specified positioning code.
     */
    List<TireModel> findByVehicleModelIdAndPositioningLocationCode(Long vehicle, String positioning);
}
