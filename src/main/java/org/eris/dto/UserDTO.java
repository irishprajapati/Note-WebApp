package org.eris.dto;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class UserDTO {
    /**
     * Used ONLY for sending user data to client.
     * Never expose sensitive fields like password.
     */
    private Long id;
    //used clear naming for api structure not used the entity used name
    @NotBlank
    private String userName;
    private List<String> roles;
}
