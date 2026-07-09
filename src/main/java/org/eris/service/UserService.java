package org.eris.service;

import org.eris.entity.User;
import org.eris.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    //constructor for password encoder
    public UserService(PasswordEncoder passwordEncoder,  UserRepository userRepository) {
       this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }
    public void saveUser(User user){
        userRepository.save(user);
    }
    public List<User> getAllUser(){
        return userRepository.findAll();
    }
    public void deleteById(Long id){
        userRepository.deleteById(id);
    }
//    public User findByUserName(String userName){
//        return userRepository.findByUser
//    }
}
