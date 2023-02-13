package com.example.Reddit.Clone.controller;

import com.example.Reddit.Clone.dto.PostDto;
import com.example.Reddit.Clone.dto.PostResponse;
import com.example.Reddit.Clone.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPost(){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPost());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostById(id));
    }

    @GetMapping("by-subreddit/{id}")
    public ResponseEntity<List<PostResponse>> getPostBySubreddit(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostBySubreddit(id));
    }

    @GetMapping("by-user/{name}")
    public ResponseEntity<List<PostResponse>> getPostByUsername(@PathVariable String name){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostByUsername(name));
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(postDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long id, @RequestBody PostDto postDto){
        return ResponseEntity.status(HttpStatus.OK).body(postService.updatePost(postDto, id));
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id){
        postService.deletePost(id);
    }
}
