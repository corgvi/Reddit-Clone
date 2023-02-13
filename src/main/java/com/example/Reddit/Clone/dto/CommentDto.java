package com.example.Reddit.Clone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
@Builder
public class CommentDto {

    private String text;
    private Long postId;
    private Instant createdDate;
    private String username;

}
