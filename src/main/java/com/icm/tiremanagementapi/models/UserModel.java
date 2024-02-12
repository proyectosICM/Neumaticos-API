package com.icm.tiremanagementapi.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.ZoneId;
import java.time.ZonedDateTime;
/**
 * Represents a user within the system.
 * This class encapsulates all relevant information about system users, including credentials,
 * personal information, and their associated roles and companies. It is fundamental for managing
 * access control and personalizing user experiences within the application.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class UserModel {
    /**
     * Unique identifier for the user, auto-generated to ensure uniqueness within the database.
     */
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Username used to log in and must be unique within the system.
     */
    @NotBlank(message = "Username cannot be blank")
    @Size(max = 20, message = "Username length must be 20 characters or less")
    private String username;

    /**
     * User's password for system access. Stored securely and used for authentication purposes.
     */
    @NotBlank(message = "Password cannot be blank")
    @Size(max = 20, message = "Password cannot exceed 20 characters or less")
    private String password;

    /**
     * User's first name, used for personalization and display purposes across the application.
     */
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 50, message = "Name cannot exceed 50 characters or less")
    private String name;

    /**
     * User's last name, complementing the first name for full identification and personalization.
     */
    @NotBlank(message = "Lastname cannot be blank")
    @Size(max = 50, message = "Lastname cannot exceed 50 characters or less")
    private String lastname;

    /**
     * User's email address, serving as an alternative contact and identification method.
     */
    @NotBlank(message = "Email cannot be blank")
    @Size(max = 50, message = "Email cannot exceed 50 characters or less")
    private String email;

    /**
     * Defines the user's role within the system, dictating their access rights and permissions.
     */
    @ManyToOne
    @JoinColumn(name = "role", nullable = false)
    private RoleModel role;

    /**
     * Associates the user with a company and depending on their role,
     * they will have control over certain company resources
     */
    @ManyToOne
    @JoinColumn(name = "company", nullable = false)
    private CompanyModel company;

    /**
     * Indicates the active or inactive status of the user, controlling their ability to access the system.
     */
    private Boolean status;

    /**
     * Timestamp marking the creation of the user record, used for auditing and historical tracking.
     */
    @Column(name = "createdat", nullable = false, updatable = false)
    @CreationTimestamp
    private ZonedDateTime createdAt = ZonedDateTime.now(ZoneId.of("America/Lima"));

    /**
     * Timestamp of the last update to the user record, providing insights into the latest modifications.
     */
    @Column(name = "updatedAt")
    @UpdateTimestamp
    private ZonedDateTime updatedAt = ZonedDateTime.now(ZoneId.of("America/Lima"));

}
