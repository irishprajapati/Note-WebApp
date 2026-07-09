package org.eris.service;

import org.eris.repository.UserRepository;
import org.eris.entity.User;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        User user = userRepository.findByUserName(userName);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + userName);
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserName())
                .password(user.getPassword())
                .roles(
                user.getRoles() != null && !user.getRoles().isEmpty()
                        ? user.getRoles().toArray(new String[0])
                        : new String[]{"USER"}
        )
                .build();
    }
}