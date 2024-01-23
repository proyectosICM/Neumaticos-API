package com.icm.TireManagementApi.Requests;

import com.icm.TireManagementApi.Models.RoleModel;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {
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

    private String email;

    private Long role;
    private Long company;
}
