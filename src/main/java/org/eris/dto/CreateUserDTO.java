package org.eris.dto;

import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
@Data
public class CreateUserDTO {
    @NotBlank
    private String userName;
    @NotBlank
    @Size(min = 5, message="Password must be atleast 5 characters")
    private String password;
}
