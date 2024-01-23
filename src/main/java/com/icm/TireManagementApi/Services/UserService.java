package com.icm.TireManagementApi.Services;

import com.icm.TireManagementApi.Models.UserModel;
import com.icm.TireManagementApi.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing operations related to User entities.
 * This service provides methods for retrieving, creating, updating, and deleting user records in the system.
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    /**
     * Retrieves a list of all users in the system.
     *
     * @return List of UserModel objects.
     */
    public List<UserModel> getAll() {
        return userRepository.findAll();
    }

    /**
     * Retrieves a specific user by its ID.
     *
     * @param id The ID of the user to retrieve.
     * @return Optional containing the UserModel if found, otherwise empty.
     */
    public Optional<UserModel> getById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Creates a new user record in the system.
     *
     * @param user The UserModel object representing the new user.
     * @return The created UserModel.
     */
    public UserModel createUser(UserModel user) {
        return userRepository.save(user);
    }

    /**
     * Updates an existing user record in the system.
     *
     * @param user The updated UserModel.
     * @param id   The ID of the user to update.
     * @return The updated UserModel if the user with the given ID is found, otherwise null.
     */
    public UserModel updateUser(UserModel user, Long id) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setUsername(user.getUsername());
                    existingUser.setPassword(user.getPassword());
                    existingUser.setName(user.getName());
                    existingUser.setLastname(user.getLastname());
                    existingUser.setEmail(user.getEmail());
                    existingUser.setRole(user.getRole()); // Assuming you have a getter for role in UserModel
                    return userRepository.save(existingUser);
                })
                .orElse(null);
    }

    /**
     * Deletes a user record from the system by its ID.
     *
     * @param id The ID of the user to delete.
     */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
