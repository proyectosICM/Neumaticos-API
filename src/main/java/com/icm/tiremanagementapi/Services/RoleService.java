package com.icm.tiremanagementapi.Services;

import com.icm.tiremanagementapi.models.RoleModel;
import com.icm.tiremanagementapi.Repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service class for managing operations related to Role entities.
 * This service provides methods for retrieving, creating, updating, and deleting role records in the system.
 */
@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    /**
     * Retrieves a paginated list of all roles in the system.
     *
     * @param page The page number to retrieve (starting from 0).
     * @param size The size of each page.
     * @return Page of RoleModel objects.
     */
    public Page<RoleModel> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return roleRepository.findAll(pageable);
    }

    /**
     * Retrieves a specific role by its ID.
     *
     * @param id The ID of the role to retrieve.
     * @return Optional containing the RoleModel if found, otherwise empty.
     */
    public Optional<RoleModel> getById(Long id) {
        return roleRepository.findById(id);
    }

    /**
     * Creates a new role record in the system.
     *
     * @param role The RoleModel object representing the new role.
     * @return The created RoleModel.
     */
    public RoleModel createRole(RoleModel role) {
        return roleRepository.save(role);
    }

    /**
     * Updates an existing role record in the system.
     *
     * @param role The updated RoleModel.
     * @param id   The ID of the role to update.
     * @return The updated RoleModel if the role with the given ID is found, otherwise null.
     */
    public RoleModel updateRole(RoleModel role, Long id) {
        return roleRepository.findById(id)
                .map(existingRole -> {
                    existingRole.setName(role.getName());
                    return roleRepository.save(existingRole);
                })
                .orElse(null);
    }

    /**
     * Deletes a role record from the system by its ID.
     *
     * @param id The ID of the role to delete.
     */
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}