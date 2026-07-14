package org.eris.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserDTO {
    @NotBlank
    private String userName;
    @NotBlank
    @Size(min = 5, message = "Passwords must of 5 characters")
    private String password;

}
