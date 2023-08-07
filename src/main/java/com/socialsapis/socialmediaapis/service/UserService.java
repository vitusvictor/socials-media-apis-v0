package com.socialsapis.socialmediaapis.service;

import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> getUser(Long id);

    ResponseEntity<?> getAllUsers();
}
