package com.icm.tiremanagementapi.Services;

import com.icm.tiremanagementapi.models.UserModel;
import com.icm.tiremanagementapi.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
     * Retrieves a paginated list of all users in the system.
     *
     * @param page The page number to retrieve (starting from 0).
     * @param size The size of each page.
     * @return Page of UserModel objects.
     */
    public Page<UserModel> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable);
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
     * Retrieves user information by searching for the user with the given username.
     *
     * @param username The username of the user to retrieve.
     * @return Optional containing the UserModel if found, otherwise empty.
     */
    public Optional<UserModel> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    /**
     * Retrieves a paginated list of users based on their role.
     *
     * @param role     The role for which to retrieve users.
     * @param pageable The pageable information for pagination.
     * @return Page of UserModel objects associated with the specified role.
     */
    public Page<UserModel> findByRoleId(Long role, Pageable pageable) {
        return userRepository.findByRoleId(role, pageable);
    }

    /**
     * Retrieves a paginated list of users based on their role and status.
     *
     * @param role     The role for which to retrieve users.
     * @param status   The status of the users to retrieve.
     * @param pageable The pageable information for pagination.
     * @return Page of UserModel objects associated with the specified role and status.
     */
    public Page<UserModel> findByRoleIdAndStatus(Long role, Boolean status, Pageable pageable) {
        return userRepository.findByRoleIdAndStatus(role, status, pageable);
    }

    /**
     * Retrieves a paginated list of users based on their role and company.
     *
     * @param role     The role for which to retrieve users.
     * @param company  The company for which to retrieve users.
     * @param pageable The pageable information for pagination.
     * @return Page of UserModel objects associated with the specified role and company.
     */
    public Page<UserModel> findByRoleIdAndCompanyId(Long role, Long company, Pageable pageable) {
        return userRepository.findByRoleIdAndCompanyId(role, company, pageable);
    }

    /**
     * Retrieves a paginated list of users based on their role, company, and status.
     *
     * @param role     The role for which to retrieve users.
     * @param company  The company for which to retrieve users.
     * @param status   The status of the users to retrieve.
     * @param pageable The pageable information for pagination.
     * @return Page of UserModel objects associated with the specified role, company, and status.
     */
    public Page<UserModel> findByRoleIdAndCompanyIdAndStatus(Long role, Long company, Boolean status, Pageable pageable) {
        return userRepository.findByRoleIdAndCompanyIdAndStatus(role, company, status, pageable);
    }


    /**0
     * Retrieves a paginated list of users based on their company.
     *
     * @param company  The company for which to retrieve users.
     * @param pageable The pageable information for pagination.
     * @return Page of UserModel objects associated with the specified company.
     */
    public Page<UserModel> findByCompanyId(Long company, Pageable pageable) {
        return userRepository.findByCompanyId(company, pageable);
    }

    /**
     * Retrieves a paginated list of users based on their company and status.
     *
     * @param company  The company for which to retrieve users.
     * @param status   The status of the users to retrieve.
     * @param pageable The pageable information for pagination.
     * @return Page of UserModel objects associated with the specified company and status.
     */
    public Page<UserModel> findByCompanyIdAndStatus(Long company, Boolean status, Pageable pageable) {
        return userRepository.findByCompanyIdAndStatus(company, status, pageable);
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
