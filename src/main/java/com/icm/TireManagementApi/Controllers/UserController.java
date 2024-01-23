package com.icm.TireManagementApi.Controllers;

import com.icm.TireManagementApi.Models.UserModel;
import com.icm.TireManagementApi.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    /**
     * Retrieves a list of all users in the system.
     *
     * @return List of UserModel objects.
     */
    @GetMapping
    public List<UserModel> getAll() {
        return userService.getAll();
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
