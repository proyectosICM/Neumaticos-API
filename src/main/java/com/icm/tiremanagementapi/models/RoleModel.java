package com.icm.tiremanagementapi.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
/**
 * Defines the access control roles within the Tire Management System.
 * Each role encapsulates a set of permissions, determining what actions a user can perform.
 * This enables fine-grained access control and customization of user capabilities based on their responsibilities.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "role")
public class RoleModel {
    /**
     * Unique identifier for the role, serving as the primary key within the database.
     * This ID facilitates efficient indexing and retrieval of role records.
     */
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Descriptive name of the role, reflecting the nature of the access permissions it grants.
     * The name field is crucial for administrators to assign roles to users appropriately,
     */
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 255, message = "Name cannot exceed 255 characters")
    private String name;
}
