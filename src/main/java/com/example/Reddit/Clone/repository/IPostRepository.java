package com.example.Reddit.Clone.repository;

import com.example.Reddit.Clone.model.Post;
import com.example.Reddit.Clone.model.Subreddit;
import com.example.Reddit.Clone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);
    List<Post> findByUser(User user);
}
