package com.icm.TireManagementApi.Repositories;

import com.icm.TireManagementApi.Models.CompanyModel;
import com.icm.TireManagementApi.Models.RoleModel;
import com.icm.TireManagementApi.Models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
/**
 * Repository interface for managing user-related database operations.
 * This repository extends JpaRepository to provide CRUD functionality for User entities.
 */
@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByUsername(String username);

    /**
     * Retrieves a paginated list of users based on their role.
     *
     * @param roleId   The ID of the role for which to retrieve users.
     * @param pageable The pageable information for pagination.
     * @return Page of UserModel objects associated with the specified role.
     */
    Page<UserModel> findByRoleId(Long roleId, Pageable pageable);

    /**
     * Retrieves a paginated list of users based on their role and status.
     *
     * @param roleId   The ID of the role for which to retrieve users.
     * @param status   The status of the users to retrieve.
     * @param pageable The pageable information for pagination.
     * @return Page of UserModel objects associated with the specified role and status.
     */
    Page<UserModel> findByRoleIdAndStatus(Long roleId, Boolean status, Pageable pageable);

    /**
     * Retrieves a paginated list of users based on their role and company.
     *
     * @param roleId   The ID of the role for which to retrieve users.
     * @param companyId The ID of the company for which to retrieve users.
     * @param pageable The pageable information for pagination.
     * @return Page of UserModel objects associated with the specified role and company.
     */
    Page<UserModel> findByRoleIdAndCompanyId(Long roleId, Long companyId, Pageable pageable);

    /**
     * Retrieves a paginated list of users based on their role, company, and status.
     *
     * @param roleId    The ID of the role for which to retrieve users.
     * @param companyId The ID of the company for which to retrieve users.
     * @param status    The status of the users to retrieve.
     * @param pageable  The pageable information for pagination.
     * @return Page of UserModel objects associated with the specified role, company, and status.
     */
    Page<UserModel> findByRoleIdAndCompanyIdAndStatus(Long roleId, Long companyId, Boolean status, Pageable pageable);

    /**
     * Retrieves a paginated list of users based on their company.
     *
     * @param companyId The ID of the company for which to retrieve users.
     * @param pageable  The pageable information for pagination.
     * @return Page of UserModel objects associated with the specified company.
     */
    Page<UserModel> findByCompanyId(Long companyId, Pageable pageable);

    /**
     * Retrieves a paginated list of users based on their company and status.
     *
     * @param companyId The ID of the company for which to retrieve users.
     * @param status    The status of the users to retrieve.
     * @param pageable  The pageable information for pagination.
     * @return Page of UserModel objects associated with the specified company and status.
     */
    Page<UserModel> findByCompanyIdAndStatus(Long companyId, Boolean status, Pageable pageable);
}
