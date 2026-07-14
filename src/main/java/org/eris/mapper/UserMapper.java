package org.eris.mapper;

import org.eris.dto.CreateUserDTO;
import org.eris.dto.UpdateUserDTO;
import org.eris.dto.UserResponseDTO;
import org.eris.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {
    public User toEntity(CreateUserDTO dto){
        /**check if the dto is null or not
         * if null then return null
         * if not then proceed to create next steps
        **/
        if(dto == null) return null;
        // create a new user object
        User user = new User();
        user.setUserName(dto.getUserName());
        user.setPassword(dto.getPassword());
        user.setRoles(List.of("ROLE_USER"));
        return user;
    }
    public UserResponseDTO toResponse(User user){
        //creation of new UserResponseDTO class
        UserResponseDTO dto = new UserResponseDTO();
        if(dto == null){
            return null;
        }
        dto.setId(user.getId());
        dto.setUserName(user.getUserName());
        dto.setRoles(user.getRoles());
        return dto;
    }
    public void UpdateUserDTO(UpdateUserDTO dto, User user){
        if(dto == null || user == null) return;
        if(dto.getUserName() != null){
            user.setUserName(dto.getUserName());
        }
        if(dto.getPassword() != null){
            user.setPassword(dto.getPassword());
        }
    }
}

