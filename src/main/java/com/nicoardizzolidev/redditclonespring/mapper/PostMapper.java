package com.nicoardizzolidev.redditclonespring.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;

import com.nicoardizzolidev.redditclonespring.dto.PostRequestDTO;
import com.nicoardizzolidev.redditclonespring.dto.PostResponseDTO;
import com.nicoardizzolidev.redditclonespring.model.Post;
import com.nicoardizzolidev.redditclonespring.model.Subreddit;
import com.nicoardizzolidev.redditclonespring.model.User;
import com.nicoardizzolidev.redditclonespring.repository.CommentRepository;
import com.nicoardizzolidev.redditclonespring.repository.VoteRepository;
import com.nicoardizzolidev.redditclonespring.services.AuthService;

@Mapper(componentModel = "spring")
public abstract class PostMapper {
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private VoteRepository voteRepository;
	
	@Autowired
	private AuthService authService;

	@Mapping(target="createdDate", expression="java(java.time.Instant.now())")
	@Mapping(target="description", source="postRequestDTO.description")
	@Mapping(target="user", source="userActual")
	@Mapping(target="voteCount", constant = "0")
	public abstract Post map(PostRequestDTO postRequestDTO, Subreddit subreddit, User userActual);
	
	
	@Mapping(target="id", source="post.postId")
	@Mapping(target="subredditName", source="subreddit.name")
	@Mapping(target="userName", source="user.username")
	@Mapping(target="commentCount", expression="java(commentCount(post))")
	@Mapping(target="duration", expression="java(getDuration(post))")
	public abstract PostResponseDTO mapToDto(Post post);
	
	public Integer commentCount(Post post) {
		return commentRepository.findByPost(post).size(); 
	}
	
	public String getDuration(Post post) {
		PrettyTime p = new PrettyTime();
		String formatedDate = p.format(post.getCreatedDate());
		return formatedDate;
	}
	
}
