package com.socialsapis.socialmediaapis.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.socialsapis.socialmediaapis.entity.User;
import com.socialsapis.socialmediaapis.exceptions.EmailAlreadyExists;
import com.socialsapis.socialmediaapis.repository.UserRepo;
import com.socialsapis.socialmediaapis.request.SignUpRequest;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {SignUpImpl.class, BCryptPasswordEncoder.class})
@ExtendWith(SpringExtension.class)
class SignUpImplTest {
    @Autowired
    private SignUpImpl signUpImpl;

    @MockBean
    private UserRepo userRepo;
    
    @Test
    void testSignUp() {
        User user = new User();
        user.setEmail("vitus.vitus@gmail.com");
        user.setFollowers(new ArrayList<>());
        user.setFollowing(new ArrayList<>());
        user.setId(1L);
        user.setPassword("password");
        user.setPosts(new ArrayList<>());
        user.setProfile("Profile");
        user.setRole("Role");
        user.setUsername("vitusvitus");
        Optional<User> ofResult = Optional.of(user);

        User user2 = new User();
        user2.setEmail("vitus.vitus@gmail.com");
        user2.setFollowers(new ArrayList<>());
        user2.setFollowing(new ArrayList<>());
        user2.setId(1L);
        user2.setPassword("password");
        user2.setPosts(new ArrayList<>());
        user2.setProfile("Profile");
        user2.setRole("Role");
        user2.setUsername("vitusvitus");
        Optional<User> ofResult2 = Optional.of(user2);
        when(userRepo.findByEmail(Mockito.<String>any())).thenReturn(ofResult);
        when(userRepo.findByUsername(Mockito.<String>any())).thenReturn(ofResult2);
        assertThrows(EmailAlreadyExists.class,
                () -> signUpImpl.signUp(new SignUpRequest("vitusvitus", "vitus.vitus@gmail.com", "password")));
        verify(userRepo).findByEmail(Mockito.<String>any());
        verify(userRepo).findByUsername(Mockito.<String>any());
    }

}

