package com.socialsapis.socialmediaapis.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Data
@Table(name = "user_table")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull
    private String username;

    @Email
    @Column(unique = true)
    @NotNull
    private String email;

    private String profile;
    private String password;

    @NotNull
    private String role;

    @OneToMany
    private List<Post> posts;

    @ManyToMany
    private List<User> followers; // those that are following you

    @ManyToMany
    private List<User> following; // those you are following

}
