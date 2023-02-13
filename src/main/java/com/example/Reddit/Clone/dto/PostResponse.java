package com.example.Reddit.Clone.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostResponse {
    private String postName;
    private String url;
    private String description;
    private String username;
    private String subredditName;
}
