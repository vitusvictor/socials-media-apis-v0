package com.socialsapis.socialmediaapis.response;

import com.socialsapis.socialmediaapis.entity.Comment;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostResponse {
    private Long id;
    private String content;
    private LocalDateTime createdDate;
    private BigInteger likesCount;
    private List<Comment> comments;
//    private List<AuthorResponse> authorResponse;
}
