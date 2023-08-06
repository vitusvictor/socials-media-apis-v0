package com.socialsapis.socialmediaapis.request;

import com.socialsapis.socialmediaapis.entity.Post;
import com.socialsapis.socialmediaapis.entity.User;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class CommentRequest {

    private Long postId;

    private String comment;

    private Long commentatorId;
}
