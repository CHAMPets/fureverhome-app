package com.champets.fureverhome.user.repository;

import com.champets.fureverhome.user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    UserEntity findByUsername(String userName);
    UserEntity findFirstByUsername(String username);

    UserEntity findUserById(Long id);
}