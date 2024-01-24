package com.icm.TireManagementApi.Repositories;

import com.icm.TireManagementApi.Models.CompanyModel;
import com.icm.TireManagementApi.Models.VehicleModel;
import com.icm.TireManagementApi.Models.VehicleTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
@Repository
public interface VehicleRepository extends JpaRepository<VehicleModel, Long> {
    /**
     * Retrieves a paginated list of vehicles associated with a specific company and status.
     *
     * @param companyId The ID of the company for which to retrieve vehicles.
     * @param status    The status of the vehicles to filter.
     * @param pageable  The pageable information for pagination.
     * @return Page of VehicleModel objects associated with the specified company and matching the given status.
     */
    Page<VehicleModel> findByCompanyIdAndStatus(Long companyId, Boolean status, Pageable pageable);

    /**
     * Retrieves a paginated list of vehicles based on type, status, and company.
     *
     * @param vehicleType The ID of the vehicle type.
     * @param status      The status of the vehicles to filter.
     * @param company     The ID of the company for which to retrieve vehicles.
     * @param pageable    The pageable information for pagination.
     * @return Page of VehicleModel objects based on the specified type, status, and company.
     */
    Page<VehicleModel> findByVehicleTypeIdAndStatusAndCompanyId(
            Long vehicleType, Boolean status, Long company, Pageable pageable);


    /**
     * Retrieves a paginated list of vehicles based on their type and company.
     *
     * @param vehicleType The type of vehicles to retrieve.
     * @param company     The company for which to retrieve vehicles.
     * @param pageable    The pageable information for pagination.
     * @return Page of VehicleModel objects associated with the specified type and company.
     */
    Page<VehicleModel> findByVehicleTypeIdAndCompanyId(Long vehicleType, Long company, Pageable pageable);
}
