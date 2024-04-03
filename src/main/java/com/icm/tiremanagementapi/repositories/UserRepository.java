package com.icm.tiremanagementapi.repositories;

import com.icm.tiremanagementapi.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByUsername(String username);

    List<UserModel> findByCompanyIdAndRoleIdIn(Long companyId, List<Long> roleIds);

    List<UserModel> findByRoleId(Long roleIde);

    Page<UserModel> findByRoleId(Long roleId, Pageable pageable);

    Page<UserModel> findByRoleIdAndStatus(Long roleId, Boolean status, Pageable pageable);

    Page<UserModel> findByRoleIdAndCompanyId(Long roleId, Long companyId, Pageable pageable);

    Page<UserModel> findByRoleIdAndCompanyIdAndStatus(Long roleId, Long companyId, Boolean status, Pageable pageable);

    Page<UserModel> findByCompanyId(Long companyId, Pageable pageable);

    Page<UserModel> findByCompanyIdAndStatus(Long companyId, Boolean status, Pageable pageable);


}
