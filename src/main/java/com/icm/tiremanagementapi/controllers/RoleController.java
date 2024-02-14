package com.icm.tiremanagementapi.controllers;

import com.icm.tiremanagementapi.models.RoleModel;
import com.icm.tiremanagementapi.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Handles HTTP requests related to roles in the system.
 * Supports operations such as listing, retrieving, creating, updating, and deleting companies.
 */
@RestController
@RequestMapping("api/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * Fetches a  list of all roles in the system.
     *
     * @return ResponseEntity with a Page of RoleModel objects.
     */
    @GetMapping
    public ResponseEntity<List<RoleModel>> getAllRoles() {
        List<RoleModel> roles = roleService.getAll();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    /**
     * Fetches a specific role by its ID.
     *
     * @param id The ID of the role to retrieve.
     * @return ResponseEntity containing the RoleModel if found, otherwise returns ResponseEntity with HttpStatus.NOT_FOUND.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RoleModel> getRoleById(@PathVariable Long id) {
        Optional<RoleModel> role = roleService.getById(id);
        return role.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Creates a new role record in the system.
     *
     * @param roleModel The RoleModel object representing the new role.
     * @return ResponseEntity containing the created RoleModel and HttpStatus.CREATED.
     */
    @PostMapping
    public ResponseEntity<RoleModel> createRole(@RequestBody RoleModel roleModel) {
        RoleModel createdRole = roleService.createRole(roleModel);
        return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
    }

    /**
     * Updates an existing role record in the system.
     *
     * @param roleModel The updated RoleModel.
     * @param id        The ID of the role to update.
     * @return ResponseEntity containing the updated RoleModel if found,
     * otherwise returns ResponseEntity with HttpStatus.NOT_FOUND.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RoleModel> updateRole(@RequestBody RoleModel roleModel, @PathVariable Long id) {
        RoleModel updatedRole = roleService.updateRole(roleModel, id);
        return updatedRole != null ?
                new ResponseEntity<>(updatedRole, HttpStatus.OK) :
                ResponseEntity.notFound().build();
    }

    /**
     * Deletes a role record from the system by its ID.
     *
     * @param id The ID of the role to delete.
     * @return ResponseEntity with HttpStatus.NO_CONTENT.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
