package com.icm.TireManagementApi.Controllers;

import com.icm.TireManagementApi.Models.CompanyModel;
import com.icm.TireManagementApi.Models.RoleModel;
import com.icm.TireManagementApi.Models.UserModel;
import com.icm.TireManagementApi.Services.CompanyService;
import com.icm.TireManagementApi.Services.RoleService;
import com.icm.TireManagementApi.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Controller class to manage operations related to User entities.
 * This service provides methods to retrieve, create, update, and delete user records in the system.
 */
@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private CompanyService companyService;

    /**
     * Retrieves a paginated list of all users in the system.
     *
     * @param page The page number to retrieve (starting from 0).
     * @param size The size of each page.
     * @return ResponseEntity with a Page of UserModel objects.
     */
    @GetMapping("/page")
    public ResponseEntity<Page<UserModel>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<UserModel> users = userService.getAll(page, size);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Retrieves a specific user by its ID.
     *
     * @param id The ID of the user to retrieve.
     * @return ResponseEntity containing the UserModel if found, otherwise returns ResponseEntity with HttpStatus.NOT_FOUND.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserModel> getById(@PathVariable Long id) {
        Optional<UserModel> user = userService.getById(id);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    /**
     * Retrieves a list of users based on their role.
     *
     * @param roleId   The ID of the role for which to retrieve users.
     * @param pageable The pageable information for pagination.
     * @return ResponseEntity containing the Page of UserModel objects and HttpStatus.OK.
     */
    @GetMapping("/role/{roleId}")
    public ResponseEntity<Page<UserModel>> getUsersByRole(
            @PathVariable Long roleId,
            Pageable pageable) {

        if (roleId != null) {
            Page<UserModel> users = userService.findByRoleId(roleId, pageable);
            return new ResponseEntity<>(users, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves a list of users based on their role and status.
     *
     * @param roleId   The ID of the role for which to retrieve users.
     * @param status   The status of the users to retrieve.
     * @param pageable The pageable information for pagination.
     * @return ResponseEntity containing the Page of UserModel objects and HttpStatus.OK.
     */
    @GetMapping("/role/{roleId}/status/{status}")
    public ResponseEntity<Page<UserModel>> getUsersByRoleAndStatus(
            @PathVariable Long roleId,
            @PathVariable Boolean status,
            Pageable pageable) {

        if (roleId != null) {
            Page<UserModel> users = userService.findByRoleIdAndStatus(roleId, status, pageable);
            return new ResponseEntity<>(users, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves a list of users based on their role and company.
     *
     * @param roleId   The ID of the role for which to retrieve users.
     * @param companyId The ID of the company for which to retrieve users.
     * @param pageable The pageable information for pagination.
     * @return ResponseEntity containing the Page of UserModel objects and HttpStatus.OK.
     */
    @GetMapping("/role/{roleId}/company/{companyId}")
    public ResponseEntity<Page<UserModel>> getUsersByRoleAndCompany(
            @PathVariable Long roleId,
            @PathVariable Long companyId,
            Pageable pageable) {
        if (roleId != null && companyId != null) {
            Page<UserModel> users = userService.findByRoleIdAndCompanyId(roleId, companyId, pageable);
            return new ResponseEntity<>(users, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves a list of users based on their role, company, and status.
     *
     * @param roleId   The ID of the role for which to retrieve users.
     * @param companyId The ID of the company for which to retrieve users.
     * @param status   The status of the users to retrieve.
     * @param pageable The pageable information for pagination.
     * @return ResponseEntity containing the Page of UserModel objects and HttpStatus.OK.
     */
    @GetMapping("/role/{roleId}/company/{companyId}/status/{status}")
    public ResponseEntity<Page<UserModel>> getUsersByRoleAndCompanyAndStatus(
            @PathVariable Long roleId,
            @PathVariable Long companyId,
            @PathVariable Boolean status,
            Pageable pageable) {

        if (roleId != null && companyId != null) {
            Page<UserModel> users = userService.findByRoleIdAndCompanyIdAndStatus(roleId, companyId, status, pageable);
            return new ResponseEntity<>(users, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves a paginated list of users based on their company.
     *
     * @param companyId The ID of the company for which to retrieve users.
     * @param pageable  The pageable information for pagination.
     * @return Page of UserModel objects associated with the specified company.
     */
    @GetMapping("/company/{companyId}")
    public Page<UserModel> getUsersByCompany(@PathVariable Long companyId, Pageable pageable) {
        if (companyId != null) {
            return userService.findByCompanyId(companyId, pageable);
        } else {
            // Handle the case where the company is not found
            return null;
        }
    }

    /**
     * Retrieves a paginated list of users based on their company and status.
     *
     * @param companyId The ID of the company for which to retrieve users.
     * @param status    The status of the users to retrieve.
     * @param pageable  The pageable information for pagination.
     * @return Page of UserModel objects associated with the specified company and status.
     */
    @GetMapping("/company/{companyId}/status/{status}")
    public Page<UserModel> getUsersByCompanyAndStatus(@PathVariable Long companyId, @PathVariable Boolean status, Pageable pageable) {
        // Assuming you have a method in CompanyService to get a CompanyModel by ID
        if (companyId != null) {
            return userService.findByCompanyIdAndStatus(companyId, status, pageable);
        } else {
            // Handle the case where the company is not found
            return null;
        }
    }

    /**
     * Creates a new user record in the system.
     *
     * @param userModel The UserModel object representing the new user.
     * @return ResponseEntity containing the created UserModel and HttpStatus.CREATED.
     */
    @PostMapping
    public ResponseEntity<UserModel> createUser(@RequestBody UserModel userModel) {
        UserModel createdUser = userService.createUser(userModel);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    /**
     * Updates an existing user record in the system.
     *
     * @param userModel The updated UserModel.
     * @param id        The ID of the user to update.
     * @return ResponseEntity containing the updated UserModel if found, otherwise returns ResponseEntity with HttpStatus.NOT_FOUND.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserModel> updateUser(@RequestBody UserModel userModel, @PathVariable Long id) {
        UserModel updatedUser = userService.updateUser(userModel, id);
        return updatedUser != null ?
                new ResponseEntity<>(updatedUser, HttpStatus.OK) :
                ResponseEntity.notFound().build();
    }

    /**
     * Deletes a user record from the system by its ID.
     *
     * @param id The ID of the user to delete.
     * @return ResponseEntity with HttpStatus.NO_CONTENT.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
