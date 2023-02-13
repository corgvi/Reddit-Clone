package com.example.Reddit.Clone.repository;

import com.example.Reddit.Clone.model.Comment;
import com.example.Reddit.Clone.model.Post;
import com.example.Reddit.Clone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
    List<Comment> findAllByUser(User user);
}
