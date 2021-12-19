package com.nicoardizzolidev.redditclonespring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nicoardizzolidev.redditclonespring.model.Post;
import com.nicoardizzolidev.redditclonespring.model.Subreddit;
import com.nicoardizzolidev.redditclonespring.model.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	List<Post> findAllBySubreddit(Subreddit subreddit);

	List<Post> findAllByUser(User user);

}
