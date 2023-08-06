package com.socialsapis.socialmediaapis.service.impl;

import com.socialsapis.socialmediaapis.entity.Post;
import com.socialsapis.socialmediaapis.entity.User;
import com.socialsapis.socialmediaapis.exceptions.EmailAlreadyExists;
import com.socialsapis.socialmediaapis.repository.UserRepo;
import com.socialsapis.socialmediaapis.request.SignUpRequest;
import com.socialsapis.socialmediaapis.service.SignUp;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Data
@Service
public class SignUpImpl implements SignUp {
    public final UserRepo userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public String signUp(SignUpRequest signUpRequest) {

        boolean emailExists = userRepository.findByEmail(signUpRequest.getEmail()).isPresent();
        boolean usernameExists = userRepository.findByUsername(signUpRequest.getUsername()).isPresent();

        if (emailExists) {
            throw new EmailAlreadyExists("Email already exists!");
        }

        if (usernameExists) {
            throw new EmailAlreadyExists("Username already exists!");
        }

        User user = new User();

        user.setEmail(signUpRequest.getEmail());
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(signUpRequest.getPassword()));
        user.setRole("USER");
        user.setFollowing(new ArrayList<>());
        user.setFollowers(new ArrayList<>());
        user.setPosts(new ArrayList<>());
        User user1 = userRepository.save(user);

        return "User created successfully.";
    }


}
