package com.icm.tiremanagementapi.services;

import com.icm.tiremanagementapi.models.RoleModel;
import com.icm.tiremanagementapi.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    public Optional<RoleModel> getById(Long id) {
        return roleRepository.findById(id);
    }

    public List<RoleModel> getAll() {
        return roleRepository.findAll();
    }

    public RoleModel createRole(RoleModel role) {
        return roleRepository.save(role);
    }

    public RoleModel updateRole(RoleModel role, Long id) {
        return roleRepository.findById(id)
                .map(existingRole -> {
                    existingRole.setName(role.getName());
                    return roleRepository.save(existingRole);
                })
                .orElse(null);
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}