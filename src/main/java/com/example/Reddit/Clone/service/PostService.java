package com.example.Reddit.Clone.service;

import com.example.Reddit.Clone.dto.PostDto;
import com.example.Reddit.Clone.dto.PostResponse;
import com.example.Reddit.Clone.exceptions.PostNotfoundException;
import com.example.Reddit.Clone.exceptions.SubredditNotfoundExceptions;
import com.example.Reddit.Clone.mapper.PostMapper;
import com.example.Reddit.Clone.model.Post;
import com.example.Reddit.Clone.model.Subreddit;
import com.example.Reddit.Clone.model.User;
import com.example.Reddit.Clone.repository.IPostRepository;
import com.example.Reddit.Clone.repository.ISubredditRepository;
import com.example.Reddit.Clone.repository.IUserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {
    private final IPostRepository iPostRepository;
    private final ISubredditRepository subredditRepository;
    private final PostMapper postMapper;
    private final IUserRepository userRepository;
    private final AuthService authService;
    @Transactional(readOnly = true)
    public List<PostResponse> getAllPost(){
        return iPostRepository.findAll()
                .stream()
                .map(postMapper::mapPostToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PostResponse getPostById(Long id){
        Post post = iPostRepository.findById(id).orElseThrow(() -> new PostNotfoundException("Not found Post"));
        return postMapper.mapPostToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostBySubreddit(Long subredditId){
        Subreddit subreddit = subredditRepository.findById(subredditId).orElseThrow(() -> new SubredditNotfoundExceptions("Not found post by subreddit"));
        List<Post> listPost = iPostRepository.findAllBySubreddit(subreddit);
        return listPost.stream().map(postMapper::mapPostToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostByUsername(String username){
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return iPostRepository.findByUser(user)
                .stream().map(postMapper::mapPostToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public PostDto createPost(PostDto postDto){
        Subreddit subreddit = subredditRepository.findByName(postDto.getSubredditName())
                .orElseThrow(() -> new SubredditNotfoundExceptions("Not found Post" + postDto.getSubredditName()));
        iPostRepository.save(postMapper.mapDtoToPost(postDto, subreddit, authService.getCurrentUser()));
        return postDto;
    }

    @Transactional
    public PostDto updatePost(PostDto postDto, Long id){
        Post post = iPostRepository.findById(id).orElseThrow(() -> new PostNotfoundException("Not found post"));
        post.setPostName(postDto.getPostName());
        post.setDescription(postDto.getDescription());
        post.setUrl(postDto.getUrl());
        iPostRepository.save(post);
        return postDto;
    }

    @Transactional
    public void deletePost(Long id){
        iPostRepository.deleteById(id);
    }
}
