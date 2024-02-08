package com.icm.TireManagementApi.Repositories;

import com.icm.TireManagementApi.Models.IrregularitiesTireModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IrregularitiesTireRepository extends JpaRepository<IrregularitiesTireModel, Long> {
    /**
     * Finds all irregularities associated with a specific company using pagination.
     *
     * @param companyId The ID of the company for which to retrieve tire irregularities.
     * @param pageable  The pageable information for pagination.
     * @return Page of IrregularitiesTireModel objects associated with the specified company.
     */
    Page<IrregularitiesTireModel> findByCompanyId(Long companyId, Pageable pageable);

    /**
     * Finds all irregularities associated with a specific company and vehicle using pagination.
     *
     * @param companyId The ID of the company for which to retrieve tire irregularities.
     * @param vehicleId The ID of the vehicle for which to retrieve tire irregularities.
     * @param pageable  The pageable information for pagination.
     * @return Page of IrregularitiesTireModel objects associated with the specified company and vehicle.
     */
    Page<IrregularitiesTireModel> findByCompanyIdAndVehicleModelId(Long companyId, Long vehicleId, Pageable pageable);

    /**
     * Finds the 6 most recent tire irregularity records.
     * Utilizes Spring Data JPA pagination to limit results to the top 6 entries when sorted by creation timestamp in descending order.
     *
     * @param pageable Pageable object to limit results to the top 6 entries. Should be configured with a page size of 6 and sort direction DESC on 'createdAt'.
     * @return Page of IrregularitiesTireModel containing the 6 most recent records.
     */
    @Query("SELECT i FROM IrregularitiesTireModel i ORDER BY i.createdAt DESC")
    Page<IrregularitiesTireModel> findTop6ByOrderByCreatedAtDesc(Pageable pageable);
}
