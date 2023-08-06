package com.socialsapis.socialmediaapis.service.impl;

import com.socialsapis.socialmediaapis.config.JwtUtils;
import com.socialsapis.socialmediaapis.repository.UserRepo;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import lombok.RequiredArgsConstructor;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


//import javax.transaction.Transactional;
import java.util.*;


@Service
//@Transactional
@RequiredArgsConstructor @Slf4j
public class UserServiceImpl implements UserDetailsService {

    private final UserRepo userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<com.socialsapis.socialmediaapis.entity.User> user = userRepository.findByEmail(email);
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("USER");
        if(user.isEmpty()){
            log.error("User not found");
            throw new UsernameNotFoundException("User not found");
        } else {
            log.info("User Found");

            return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(), Collections.singleton(authority));
        }
    }
}
