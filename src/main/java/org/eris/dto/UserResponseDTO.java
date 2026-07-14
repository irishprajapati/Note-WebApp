package org.eris.dto;

import lombok.Data;

import java.util.List;
@Data
public class UserResponseDTO {
    private Long id;
    private String userName;
    private List<String> roles;
}
