package com.socialsapis.socialmediaapis.service.impl;

import com.socialsapis.socialmediaapis.config.JwtUtils;
import com.socialsapis.socialmediaapis.entity.User;
import com.socialsapis.socialmediaapis.repository.UserRepo;
import com.socialsapis.socialmediaapis.request.SigninRequest;
import com.socialsapis.socialmediaapis.response.SigninResponse;
import com.socialsapis.socialmediaapis.service.Signin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class SigninImpl implements Signin {
    private final UserRepo userRepository;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userService;

    @Override
    public SigninResponse signin(SigninRequest signinRequest) {
        User user = userRepository.findByEmail(signinRequest.getEmail()).
                orElseThrow(()-> new UsernameNotFoundException("User not found!"));

        try{
            authenticationManager.authenticate( new UsernamePasswordAuthenticationToken
                    (signinRequest.getEmail(), signinRequest.getPassword())
            );
        } catch (BadCredentialsException ex){
            throw new UsernameNotFoundException("Invalid Credential");
        }
        final UserDetails userDetails = userService.loadUserByUsername(signinRequest.getEmail());
        log.info("loaded user by username");
        String jwt = jwtUtils.generateToken(userDetails);
        System.out.println("jwt ->>>>>> "+jwt);
        return new SigninResponse("Successfully signed in", jwt);

    }


}
