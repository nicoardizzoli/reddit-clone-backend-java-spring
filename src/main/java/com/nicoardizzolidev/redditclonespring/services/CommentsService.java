package com.nicoardizzolidev.redditclonespring.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.nicoardizzolidev.redditclonespring.dto.CommentsDTO;
import com.nicoardizzolidev.redditclonespring.exceptions.PostNotFoundException;
import com.nicoardizzolidev.redditclonespring.exceptions.UsernameNotFoundException;
import com.nicoardizzolidev.redditclonespring.mapper.CommentMapper;
import com.nicoardizzolidev.redditclonespring.model.Comment;
import com.nicoardizzolidev.redditclonespring.model.NotificationEmail;
import com.nicoardizzolidev.redditclonespring.model.Post;
import com.nicoardizzolidev.redditclonespring.model.User;
import com.nicoardizzolidev.redditclonespring.repository.CommentRepository;
import com.nicoardizzolidev.redditclonespring.repository.PostRepository;
import com.nicoardizzolidev.redditclonespring.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentsService {

	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final AuthService authService;
	private final CommentMapper commentMapper;
	private final CommentRepository commentRepository;
	private final MailContentBuilder mailContentBuilder;
	private final MailService mailService;
	
	public void save (CommentsDTO commentsDTO) {
		Post post = postRepository.findById(commentsDTO.getPostId()).orElseThrow(() -> new PostNotFoundException(commentsDTO.getPostId().toString()));
		
		User currentUser = authService.getCurrentUser();
		
		Comment comment = commentMapper.map(commentsDTO, post, currentUser);
		
		commentRepository.save(comment);
		
		String message = mailContentBuilder.build(post.getUser().getUsername() + " posted a comment on your post." + post.getUrl());
		
		sendCommentNotification(message, post.getUser());
	}

	private void sendCommentNotification(String message, User user) {
		mailService.sendMail(new NotificationEmail(user.getUsername() + " commented on your post", user.getEmail(), message));
		
	}

	public List<CommentsDTO> getAllCommentsForPost(Long postId) {
		Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
		List<CommentsDTO> listComments = commentRepository.findAllByPost(post).stream().map(commentMapper::mapToDto).collect(Collectors.toList());
		return listComments;
	}
	
	public List<CommentsDTO> getAllCommentsForUser(String username) {
		User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
		List<CommentsDTO> listComments = commentRepository.findAllByUser(user).stream().map(commentMapper::mapToDto).collect(Collectors.toList());
		return listComments;
	}
}
