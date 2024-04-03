package com.icm.tiremanagementapi.services;

import com.icm.tiremanagementapi.models.UserModel;
import com.icm.tiremanagementapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<UserModel> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<UserModel> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<String> findByCompanyIdAndRoleIdIn(Long companyId, Long roleId1, Long roleId2) {
        List<UserModel> users = userRepository.findByCompanyIdAndRoleIdIn(companyId, List.of(roleId1, roleId2));
        List<String> emails = new ArrayList<>();
        for (UserModel user : users) {
            emails.add(user.getEmail());
        }
        return emails;
    }

    public List<UserModel> findAll() {
        return userRepository.findAll();
    }

    public Page<UserModel> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable);
    }

    public List<UserModel> findByRoleId(Long role) {
        return userRepository.findByRoleId(role);
    }

    public Page<UserModel> findByRoleId(Long role, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findByRoleId(role, pageable);
    }

    public Page<UserModel> findByCompanyId(Long company, Pageable pageable) {
        return userRepository.findByCompanyId(company, pageable);
    }

    public Page<UserModel> findByRoleIdAndStatus(Long role, Boolean status, Pageable pageable) {
        return userRepository.findByRoleIdAndStatus(role, status, pageable);
    }

    public Page<UserModel> findByRoleIdAndCompanyId(Long role, Long company, Pageable pageable) {
        return userRepository.findByRoleIdAndCompanyId(role, company, pageable);
    }

    public Page<UserModel> findByCompanyIdAndStatus(Long company, Boolean status, Pageable pageable) {
        return userRepository.findByCompanyIdAndStatus(company, status, pageable);
    }

    public Page<UserModel> findByRoleIdAndCompanyIdAndStatus(Long role, Long company, Boolean status, Pageable pageable) {
        return userRepository.findByRoleIdAndCompanyIdAndStatus(role, company, status, pageable);
    }

    public UserModel createUser(UserModel user) {
        return userRepository.save(user);
    }

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

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
