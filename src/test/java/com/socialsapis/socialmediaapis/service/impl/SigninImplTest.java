package com.socialsapis.socialmediaapis.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.socialsapis.socialmediaapis.config.JwtUtils;
import com.socialsapis.socialmediaapis.entity.User;
import com.socialsapis.socialmediaapis.repository.UserRepo;
import com.socialsapis.socialmediaapis.request.SigninRequest;
import com.socialsapis.socialmediaapis.response.SigninResponse;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {SigninImpl.class})
@ExtendWith(SpringExtension.class)
class SigninImplTest {
    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtUtils jwtUtils;

    @Autowired
    private SigninImpl signinImpl;

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private UserServiceImpl userServiceImpl;


    @Test
    void testSignin() throws AuthenticationException {
        com.socialsapis.socialmediaapis.entity.User user = new com.socialsapis.socialmediaapis.entity.User();
        user.setEmail("vitus.vitus@gmail.com");
        user.setFollowers(new ArrayList<>());
        user.setFollowing(new ArrayList<>());
        user.setId(1L);
        user.setPassword("password");
        user.setPosts(new ArrayList<>());
        user.setProfile("Profile");
        user.setRole("Role");
        user.setUsername("vitusvitus");
        Optional<com.socialsapis.socialmediaapis.entity.User> ofResult = Optional.of(user);
        when(userRepo.findByEmail(Mockito.<String>any())).thenReturn(ofResult);
        when(jwtUtils.generateToken(Mockito.<UserDetails>any())).thenReturn("ABC123");
        when(userServiceImpl.loadUserByUsername(Mockito.<String>any()))
                .thenReturn(new org.springframework.security.core.userdetails.User("vitusvitus", "password", new ArrayList<>()));
        when(authenticationManager.authenticate(Mockito.<Authentication>any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        SigninResponse actualSigninResult = signinImpl.signin(new SigninRequest("password", "vitus.vitus@gmail.com"));
        assertEquals("Successfully signed in", actualSigninResult.getMessage());
        assertEquals("ABC123", actualSigninResult.getToken());
        verify(userRepo).findByEmail(Mockito.<String>any());
        verify(jwtUtils).generateToken(Mockito.<UserDetails>any());
        verify(userServiceImpl).loadUserByUsername(Mockito.<String>any());
        verify(authenticationManager).authenticate(Mockito.<Authentication>any());
    }
}

