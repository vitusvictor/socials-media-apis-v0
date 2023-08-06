package com.socialsapis.socialmediaapis.repository;

import com.socialsapis.socialmediaapis.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
}
