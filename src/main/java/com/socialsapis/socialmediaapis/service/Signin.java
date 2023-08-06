package com.socialsapis.socialmediaapis.service;

import com.socialsapis.socialmediaapis.request.SigninRequest;
import com.socialsapis.socialmediaapis.response.SigninResponse;

public interface Signin {
    SigninResponse signin(SigninRequest signRequest);

//    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;
}
