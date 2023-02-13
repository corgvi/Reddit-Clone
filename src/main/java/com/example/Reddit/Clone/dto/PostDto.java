package com.example.Reddit.Clone.dto;

import com.example.Reddit.Clone.model.Subreddit;
import com.example.Reddit.Clone.model.User;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import java.time.Instant;

@Data
@Builder
public class PostDto {
    private String postName;
    private String url;
    private String description;
    private String subredditName;
}
