package com.socialsapis.socialmediaapis.exceptions;

public class PostNotFoundException extends RuntimeException{
    public PostNotFoundException(String message) {
        super(message);
    }
}
