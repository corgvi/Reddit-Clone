package com.example.Reddit.Clone.controller;

import com.example.Reddit.Clone.dto.SubredditDto;
import com.example.Reddit.Clone.model.Subreddit;
import com.example.Reddit.Clone.service.SubredditService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subreddit")
@Slf4j
public class SubredditController {


    private final SubredditService subredditService;

    public SubredditController(SubredditService subredditService) {
        this.subredditService = subredditService;
    }

    @GetMapping
    public ResponseEntity<List<SubredditDto>> getAllSubReddit(){
        return ResponseEntity.status(HttpStatus.OK).body(subredditService.getAllSubreddit());
    }
    @GetMapping("/{id}")
    public ResponseEntity<SubredditDto> getSubRedditById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(subredditService.getSubredditById(id));
    }

    @PostMapping("")
    public ResponseEntity<SubredditDto> createSubreddit(@RequestBody SubredditDto subreddit){
        return ResponseEntity.status(HttpStatus.CREATED).body(subredditService.createSubreddit(subreddit));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubredditDto> updateSubreddit(@PathVariable Long id, @RequestBody SubredditDto subredditDto){
        return ResponseEntity.status(HttpStatus.OK).body(subredditService.updateSubreddit(subredditDto, id));
    }

    @DeleteMapping("/{id}")
    public void deleteSubreddit(@PathVariable Long id){
        subredditService.deleteSubreddit(id);
    }

}
