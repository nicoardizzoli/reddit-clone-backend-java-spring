package com.nicoardizzolidev.redditclonespring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nicoardizzolidev.redditclonespring.model.Comment;
import com.nicoardizzolidev.redditclonespring.model.Post;
import com.nicoardizzolidev.redditclonespring.model.User;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findAllByPost(Post post);

	List<Comment> findAllByUser(User user);

	List<Comment> findByPost(Post post);

}
