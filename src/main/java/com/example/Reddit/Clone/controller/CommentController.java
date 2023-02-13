package com.example.Reddit.Clone.controller;

import com.example.Reddit.Clone.dto.CommentDto;
import com.example.Reddit.Clone.exceptions.PostNotfoundException;
import com.example.Reddit.Clone.model.Post;
import com.example.Reddit.Clone.repository.IPostRepository;
import com.example.Reddit.Clone.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto){
        commentService.createComment(commentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(commentDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto, @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.updateComment(commentDto, id));
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id){
        commentService.deleteComment(id);
    }

    @GetMapping("/by-post/{postId}")
    public ResponseEntity<List<CommentDto>> getAllCommentsForPost(@PathVariable Long postId){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentsForPost(postId));
    }

    @GetMapping("by-user/{username}")
    public ResponseEntity<List<CommentDto>> getAllCommentsForUser(@PathVariable String username){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentsForUser(username));
    }
}
