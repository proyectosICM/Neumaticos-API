package com.icm.tiremanagementapi.repositories;

import com.icm.tiremanagementapi.models.IrregularitiesTireModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

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
    List<IrregularitiesTireModel> findByCompanyIdAndVehicleModelId(Long companyId, Long vehicleId);

    /**
     * Retrieves the 6 most recent irregularities for a specific vehicle model.
     * This method utilizes Spring Data JPA pagination to fetch the top 6 entries sorted by creation timestamp in descending order, ensuring only the most recent records are returned for the given vehicle model.
     *
     * @param vehicleModelId The ID of the vehicle model for which to retrieve the irregularities.
     * @param pageable       A Pageable object to limit results to the top 6 entries. It should be configured with a page size of 6 and sort direction DESC on 'createdAt'.
     * @return A page of IrregularitiesTireModel containing the 6 most recent records for the specified vehicle model.
     */
    Page<IrregularitiesTireModel> findByVehicleModelIdOrderByCreatedAtDesc(Long vehicleModelId, Pageable pageable);

    @Query("SELECT it FROM IrregularitiesTireModel it WHERE it.nameIrregularity = :nameIrregularity AND it.tireSensorModel.id = :tireId AND it.createdAt >= :startTime")
    List<IrregularitiesTireModel> findByNameIrregularityAndTireSensorModelIdWithinTimeframe(@Param("nameIrregularity") String nameIrregularity, @Param("tireId") Long tireId, @Param("startTime") ZonedDateTime startTime);
    List<IrregularitiesTireModel> findByNameIrregularityAndTireSensorModelIdAndCreatedAtGreaterThanEqual(String  name, Long tireId,  ZonedDateTime startTime);
}
