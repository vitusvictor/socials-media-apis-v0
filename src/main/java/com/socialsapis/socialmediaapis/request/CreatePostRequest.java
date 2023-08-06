package com.socialsapis.socialmediaapis.request;

import com.socialsapis.socialmediaapis.entity.Comment;
import com.socialsapis.socialmediaapis.entity.User;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreatePostRequest {
    private String content;
    @NotNull(message = "Please include author id.")
    private Long userId;
}
