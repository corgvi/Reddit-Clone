package com.example.Reddit.Clone.mapper;

import com.example.Reddit.Clone.dto.PostDto;
import com.example.Reddit.Clone.dto.PostResponse;
import com.example.Reddit.Clone.model.Post;
import com.example.Reddit.Clone.model.Subreddit;
import com.example.Reddit.Clone.model.User;
import com.example.Reddit.Clone.service.AuthService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Autowired
    private AuthService authService;


    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postDto.description")
    @Mapping(target = "subreddit", source = "subreddit")
    @Mapping(target = "user", source = "user")
    public abstract Post mapDtoToPost(PostDto postDto, Subreddit subreddit, User user);

    @Mapping(target = "postName", source = "postName")
    @Mapping(target = "url", source = "url")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "username", source = "user.username")
    public abstract PostResponse mapPostToDto(Post post);
}
