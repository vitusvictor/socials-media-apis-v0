package com.socialsapis.socialmediaapis.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.socialsapis.socialmediaapis.entity.User;
import com.socialsapis.socialmediaapis.exceptions.UserNotFoundException;
import com.socialsapis.socialmediaapis.repository.UserRepo;
import com.socialsapis.socialmediaapis.response.FollowerResponse;
import com.socialsapis.socialmediaapis.response.UserResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {
    @MockBean
    private UserRepo userRepo;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Test
    void testGetUser() {
        User user = new User();
        user.setEmail("vitus.vitus@gmail.com");
        ArrayList<User> followers = new ArrayList<>();
        user.setFollowers(followers);
        user.setFollowing(new ArrayList<>());
        user.setId(1L);
        user.setPassword("password");
        user.setPosts(new ArrayList<>());
        user.setProfile("Profile");
        user.setRole("Role");
        user.setUsername("vitusvitus");
        Optional<User> ofResult = Optional.of(user);
        when(userRepo.findById(Mockito.<Long>any())).thenReturn(ofResult);
        ResponseEntity<?> actualUser = userServiceImpl.getUser(1L);
        assertTrue(actualUser.hasBody());
        assertEquals(200, actualUser.getStatusCodeValue());
        assertTrue(actualUser.getHeaders().isEmpty());
        assertEquals(1L, ((UserResponse) actualUser.getBody()).getId().longValue());
        assertEquals(followers, ((UserResponse) actualUser.getBody()).getFollowers());
        assertEquals("vitus.vitus@gmail.com", ((UserResponse) actualUser.getBody()).getEmail());
        assertEquals(followers, ((UserResponse) actualUser.getBody()).getFollowing());
        assertEquals("vitusvitus", ((UserResponse) actualUser.getBody()).getUsername());
        verify(userRepo).findById(Mockito.<Long>any());
    }
    
    @Test
    void testGetAllUsers() {
        ArrayList<User> userList = new ArrayList<>();
        when(userRepo.findAll()).thenReturn(userList);
        ResponseEntity<?> actualAllUsers = userServiceImpl.getAllUsers();
        assertEquals(userList, actualAllUsers.getBody());
        assertEquals(200, actualAllUsers.getStatusCodeValue());
        assertTrue(actualAllUsers.getHeaders().isEmpty());
        verify(userRepo).findAll();
    }
    
}

