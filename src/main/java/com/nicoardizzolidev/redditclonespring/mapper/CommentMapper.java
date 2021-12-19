package com.nicoardizzolidev.redditclonespring.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.nicoardizzolidev.redditclonespring.dto.CommentsDTO;
import com.nicoardizzolidev.redditclonespring.model.Comment;
import com.nicoardizzolidev.redditclonespring.model.Post;
import com.nicoardizzolidev.redditclonespring.model.User;

@Mapper(componentModel = "spring")
public interface CommentMapper {
	
	@Mapping(target = "id", ignore=true)
	@Mapping(target = "createdDate", expression ="java(java.time.Instant.now())")
	@Mapping(target = "post", source ="postActual")
	@Mapping(target = "user", source ="userActual")
	Comment map(CommentsDTO commentsDTO, Post postActual, User userActual);
	
	
	@Mapping(target="postId", source="comment.post.postId")
	@Mapping(target="userName", source="comment.user.username")
	CommentsDTO mapToDto(Comment comment);
	
}
