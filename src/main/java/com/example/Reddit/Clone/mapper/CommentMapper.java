package com.example.Reddit.Clone.mapper;

import com.example.Reddit.Clone.dto.CommentDto;
import com.example.Reddit.Clone.model.Comment;
import com.example.Reddit.Clone.model.Post;
import com.example.Reddit.Clone.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "text", source = "commentDto.text")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "user", source = "user")
    public Comment mapDtoToComment(CommentDto commentDto, Post post, User user);

    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "username", expression = "java(comment.getUser().getUsername())")
    public CommentDto mapCommentToDto(Comment comment);
}
