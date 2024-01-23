package com.icm.TireManagementApi.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Represents a user within the system.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class UserModel {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username cannot be blank")
    @Size(max = 20, message = "Username length must be 20 characters or less")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @Size(max = 20, message = "Password cannot exceed 20 characters or less")
    private String password;

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 50, message = "Name cannot exceed 50 characters or less")
    private String name;

    @NotBlank(message = "Lastname cannot be blank")
    @Size(max = 50, message = "Lastname cannot exceed 50 characters or less")
    private String lastname;

    @NotBlank(message = "Email cannot be blank")
    @Size(max = 50, message = "Email cannot exceed 50 characters or less")
    private String email;

    /**
    * Date and time this user was created.
     */
    @Column(name = "createdat", nullable = false, updatable = false)
    @CreationTimestamp
    private ZonedDateTime createdAt = ZonedDateTime.now(ZoneId.of("America/Lima"));

    /**
     * Date and time this user was updated.
     */
    @Column(name = "updatedAt")
    @UpdateTimestamp
    private ZonedDateTime updatedAt = ZonedDateTime.now(ZoneId.of("America/Lima"));


    /**
     * Role associated with the user to manage their access and options
     */
    @ManyToOne
    @JoinColumn(name = "role", nullable = false)
    private RoleModel role;

    @ManyToOne
    @JoinColumn(name = "company", nullable = false)
    private CompanyModel company;

}
