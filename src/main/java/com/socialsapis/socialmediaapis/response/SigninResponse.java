package com.socialsapis.socialmediaapis.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SigninResponse {
    String message;
    String token;
}

