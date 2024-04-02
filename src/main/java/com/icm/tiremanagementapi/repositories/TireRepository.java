package com.icm.tiremanagementapi.repositories;

import com.icm.tiremanagementapi.models.TireModel;
import com.icm.tiremanagementapi.models.TireStatus;
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
     * @param codename The identification code of the tire to retrieve.
     * @return Optional containing the TireModel if found, otherwise empty.
     */
    Optional<TireModel> findByCodnameAndCompanyModelIdAndStatus(String codname, Long companyModelId, TireStatus status);

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
    List<TireModel> findByStatus(TireStatus status);


    /**
     * Finds all tires associated with a specific vehicle and status using pagination.
     *
     * @param vehicle  The vehicle for which to retrieve tires.
     * @param status   The status of the tires to retrieve.
     * @param pageable The pageable information for pagination.
     * @return Page of TireModel objects associated with the specified vehicle and status.
     */
    Page<TireModel> findByVehicleModelIdAndStatus(Long vehicle, Boolean status, Pageable pageable);

    List<TireModel> findByCompanyModelIdAndStatus(Long company, Boolean status);

    Optional<TireModel> findByVehicleModelIdAndPositioningModelId(Long vehicleId, Long positioningId);

    Page<TireModel> findByCompanyModelId(Long companyId, Pageable pageable);
}
