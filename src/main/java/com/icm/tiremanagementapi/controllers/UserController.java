package com.icm.tiremanagementapi.controllers;

import com.icm.tiremanagementapi.models.UserModel;
import com.icm.tiremanagementapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserModel> findById(@PathVariable Long id) {
        Optional<UserModel> user = userService.findById(id);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/info/{username}")
    public ResponseEntity<UserModel> findByUsername(@PathVariable("username") String username){
        Optional<UserModel> user = userService.findByUsername(username);

        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/emailsList/{companyId}/{roleId1}/{roleId2}")
    public ResponseEntity<List<String>> findByCompanyIdAndRoleIdIn(
            @PathVariable Long companyId,
            @PathVariable Long roleId1,
            @PathVariable Long roleId2) {
        List<String> emails = userService.findByCompanyIdAndRoleIdIn(companyId, roleId1, roleId2);
        return ResponseEntity.ok(emails);
    }

    @GetMapping
    public ResponseEntity<List<UserModel>> findAll() {
        List<UserModel> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<UserModel>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<UserModel> users = userService.findAll(page, size);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/role")
    public ResponseEntity<List<UserModel>> findByRoleId(@RequestParam Long roleId) {

        if (roleId != null) {
            List<UserModel> users = userService.findByRoleId(roleId);
            return new ResponseEntity<>(users, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/role/page")
    public ResponseEntity<Page<UserModel>> findByRoleId(
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

    @GetMapping("/company")
    public Page<UserModel> findByCompanyId(@RequestParam Long companyId, Pageable pageable) {
        if (companyId != null) {
            return userService.findByCompanyId(companyId, pageable);
        } else {
            return null;
        }
    }

    @GetMapping("/roleAndStatus")
    public ResponseEntity<Page<UserModel>> findByRoleIdAndStatus(
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

    @GetMapping("/roleAndCompany")
    public ResponseEntity<Page<UserModel>> findByRoleIdAndCompanyId(
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

    @GetMapping("/companyAndStatus/")
    public Page<UserModel> findByCompanyIdAndStatus(@RequestParam Long companyId, @RequestParam Boolean status, Pageable pageable) {
        if (companyId != null) {
            return userService.findByCompanyIdAndStatus(companyId, status, pageable);
        } else {
            return null;
        }
    }

    @GetMapping("/roleCompanyStatus")
    public ResponseEntity<Page<UserModel>> findByRoleIdAndCompanyIdAndStatus(
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

    @PostMapping
    public ResponseEntity<UserModel> createUser(@RequestBody UserModel userModel) {
        UserModel createdUser = userService.createUser(userModel);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserModel> updateUser(@RequestBody UserModel userModel, @PathVariable Long id) {
        UserModel updatedUser = userService.updateUser(userModel, id);
        return updatedUser != null ?
                new ResponseEntity<>(updatedUser, HttpStatus.OK) :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
