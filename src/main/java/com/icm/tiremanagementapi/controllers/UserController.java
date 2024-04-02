package com.icm.tiremanagementapi.controllers;

import com.icm.tiremanagementapi.models.UserModel;
import com.icm.tiremanagementapi.services.CompanyService;
import com.icm.tiremanagementapi.services.RoleService;
import com.icm.tiremanagementapi.services.UserService;
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
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private CompanyService companyService;


    @GetMapping("/emailsList/{companyId}/{roleId1}/{roleId2}")
    public ResponseEntity<List<String>> getEmailsByCompanyAndRole(
            @PathVariable Long companyId,
            @PathVariable Long roleId1,
            @PathVariable Long roleId2) {
        List<String> emails = userService.getEmailsByCompanyAndRole(companyId, roleId1, roleId2);
        return ResponseEntity.ok(emails);
    }

    /**
     * Fetches a list of all users in the system.
     *
     * @return ResponseEntity with a Page of UserModel objects.
     */
    @GetMapping()
    public ResponseEntity<List<UserModel>> getAllUsers() {
        List<UserModel> users = userService.getAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    /**
     * Fetches a paginated list of all users in the system.
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
     * Fetches user information based on the username.
     *
     * @param username The username of the user to retrieve.
     * @return ResponseEntity containing the UserModel if found, otherwise returns ResponseEntity with HttpStatus.NOT_FOUND.
     */
    @GetMapping("/info/{username}")
    public ResponseEntity<UserModel> findUserByUsername(@PathVariable("username") String username){
        Optional<UserModel> user = userService.findUserByUsername(username);

        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Fetches a list of users based on their role.
     *
     * @param roleId   The ID of the role for which to retrieve users.
     * @return ResponseEntity containing the Page of UserModel objects and HttpStatus.OK.
     */
    @GetMapping("/role")
    public ResponseEntity<List<UserModel>> getUsersByRole(
            @RequestParam Long roleId) {

        if (roleId != null) {
            List<UserModel> users = userService.findByRoleId(roleId);
            return new ResponseEntity<>(users, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Fetches a paginated list of users based on their role.
     *
     * @param roleId   The ID of the role for which to retrieve users.
     * @param page The page number to retrieve (starting from 0).
     * @param size The size of each page.
     * @return ResponseEntity containing the Page of UserModel objects and HttpStatus.OK.
     */
    @GetMapping("/role/page")
    public ResponseEntity<Page<UserModel>> getUsersByRole(
            @RequestParam Long roleId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        if (roleId != null) {
            Page<UserModel> users = userService.findByRoleId(roleId, page, size);
            return new ResponseEntity<>(users, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Fetches a list of users based on their role and status.
     *
     * @param roleId   The ID of the role for which to retrieve users.
     * @param status   The status of the users to retrieve.
     * @param pageable The pageable information for pagination.
     * @return ResponseEntity containing the Page of UserModel objects and HttpStatus.OK.
     */
    @GetMapping("/roleAndStatus")
    public ResponseEntity<Page<UserModel>> getUsersByRoleAndStatus(
            @RequestParam Long roleId,
            @RequestParam Boolean status,
            Pageable pageable) {

        if (roleId != null) {
            Page<UserModel> users = userService.findByRoleIdAndStatus(roleId, status, pageable);
            return new ResponseEntity<>(users, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Fetches a list of users based on their role and company.
     *
     * @param roleId   The ID of the role for which to retrieve users.
     * @param companyId The ID of the company for which to retrieve users.
     * @param pageable The pageable information for pagination.
     * @return ResponseEntity containing the Page of UserModel objects and HttpStatus.OK.
     */
    @GetMapping("/roleAndCompany")
    public ResponseEntity<Page<UserModel>> getUsersByRoleAndCompany(
            @RequestParam Long roleId,
            @RequestParam Long companyId,
            Pageable pageable) {
        if (roleId != null && companyId != null) {
            Page<UserModel> users = userService.findByRoleIdAndCompanyId(roleId, companyId, pageable);
            return new ResponseEntity<>(users, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Fetches a list of users based on their role, company, and status.
     *
     * @param roleId   The ID of the role for which to retrieve users.
     * @param companyId The ID of the company for which to retrieve users.
     * @param status   The status of the users to retrieve.
     * @param pageable The pageable information for pagination.
     * @return ResponseEntity containing the Page of UserModel objects and HttpStatus.OK.
     */
    @GetMapping("/roleCompanyStatus")
    public ResponseEntity<Page<UserModel>> getUsersByRoleAndCompanyAndStatus(
            @RequestParam Long roleId,
            @RequestParam Long companyId,
            @RequestParam Boolean status,
            Pageable pageable) {

        if (roleId != null && companyId != null) {
            Page<UserModel> users = userService.findByRoleIdAndCompanyIdAndStatus(roleId, companyId, status, pageable);
            return new ResponseEntity<>(users, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Fetches a paginated list of users based on their company.
     *
     * @param companyId The ID of the company for which to retrieve users.
     * @param pageable  The pageable information for pagination.
     * @return Page of UserModel objects associated with the specified company.
     */
    @GetMapping("/company")
    public Page<UserModel> getUsersByCompany(@RequestParam Long companyId, Pageable pageable) {
        if (companyId != null) {
            return userService.findByCompanyId(companyId, pageable);
        } else {
            // Handle the case where the company is not found
            return null;
        }
    }

    /**
     * Fetches a paginated list of users based on their company and status.
     *
     * @param companyId The ID of the company for which to retrieve users.
     * @param status    The status of the users to retrieve.
     * @param pageable  The pageable information for pagination.
     * @return Page of UserModel objects associated with the specified company and status.
     */
    @GetMapping("/companyAndStatus/")
    public Page<UserModel> getUsersByCompanyAndStatus(@RequestParam Long companyId, @RequestParam Boolean status, Pageable pageable) {
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
