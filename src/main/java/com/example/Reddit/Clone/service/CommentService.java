package com.example.Reddit.Clone.service;

import com.example.Reddit.Clone.dto.CommentDto;
import com.example.Reddit.Clone.exceptions.CommentNotFoundException;
import com.example.Reddit.Clone.exceptions.PostNotfoundException;
import com.example.Reddit.Clone.mapper.CommentMapper;
import com.example.Reddit.Clone.model.Comment;
import com.example.Reddit.Clone.model.NotificationEmail;
import com.example.Reddit.Clone.model.Post;
import com.example.Reddit.Clone.model.User;
import com.example.Reddit.Clone.repository.ICommentRepository;
import com.example.Reddit.Clone.repository.IPostRepository;
import com.example.Reddit.Clone.repository.IUserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class CommentService {

    private final ICommentRepository commentRepository;
    private final IUserRepository userRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final IPostRepository iPostRepository;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;
    private static final String postUrl = "";

    private void sendCommentNotification(String message, User user){
        mailService.sendMail(new NotificationEmail(user.getUsername() + "Commented on your post", user.getEmail(), message));
    }
    @Transactional(readOnly = true)
    public List<CommentDto> getAllComment(){
        return commentRepository.findAll()
                .stream().map(commentMapper::mapCommentToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CommentDto getById(Long id){
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Not found comment: " + id));
        return commentMapper.mapCommentToDto(comment);
    }

    @Transactional(readOnly = true)
    public List<CommentDto> getAllCommentsForPost(Long postId){
        Post post = iPostRepository.findById(postId).orElseThrow(() -> new PostNotfoundException("Not found post: " + postId));
        return commentRepository.findByPost(post).stream()
                .map(commentMapper::mapCommentToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CommentDto> getAllCommentsForUser(String username){
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return commentRepository.findAllByUser(user)
                .stream().map(commentMapper::mapCommentToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentDto createComment(CommentDto commentDto) {
        Post post = iPostRepository.findById(commentDto.getPostId()).orElseThrow(() -> new PostNotfoundException("Not found post: " + commentDto.getPostId().toString()));
        Comment comment = commentMapper.mapDtoToComment(commentDto, post, authService.getCurrentUser());
        commentRepository.save(comment);
        return commentDto;
    }
    @Transactional
    public CommentDto updateComment(CommentDto commentDto, Long id){
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException("Not found comment: " + id));
        comment.setText(commentDto.getText());
        commentRepository.save(comment);
        return commentDto;
    }
    @Transactional
    public void deleteComment(Long id){
        commentRepository.deleteById(id);
    }

}
