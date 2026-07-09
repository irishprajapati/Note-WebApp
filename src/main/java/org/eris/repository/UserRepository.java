package org.eris.repository;

import org.eris.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);
    User deleteByUserName(String userName);

}