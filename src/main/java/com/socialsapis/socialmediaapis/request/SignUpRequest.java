package com.socialsapis.socialmediaapis.request;

import com.socialsapis.socialmediaapis.entity.Post;
import com.socialsapis.socialmediaapis.entity.User;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SignUpRequest {

    private String username;

    private String email;

    private String password;
}
