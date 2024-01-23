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
     * Finds all vehicles associated with a specific company and in a given state using pagination.
     *
     * @param company   The company for which to retrieve vehicles.
     * @param status    The status of the vehicles to filter.
     * @param pageable  The pageable information for pagination.
     * @return Page of VehicleModel objects associated with the specified company and matching the given status.
     */
    Page<VehicleModel> findByCompanyAndStatus(CompanyModel company, Boolean status, Pageable pageable);

    /**
     * Finds vehicles based on type, status, and company using pagination.
     *
     * @param vehicleType The type of the vehicle.
     * @param status      The status of the vehicles to filter.
     * @param company     The company for which to retrieve vehicles.
     * @param pageable    The pageable information for pagination.
     * @return Page of VehicleModel objects based on the specified type, status, and company.
     */
    Page<VehicleModel> findByVehicleTypeModelAndStatusAndCompany(
            VehicleTypeModel vehicleType, Boolean status, CompanyModel company, Pageable pageable);
}
