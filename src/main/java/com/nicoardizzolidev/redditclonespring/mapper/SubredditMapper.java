package com.nicoardizzolidev.redditclonespring.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.nicoardizzolidev.redditclonespring.dto.SubredditDTO;
import com.nicoardizzolidev.redditclonespring.model.Post;
import com.nicoardizzolidev.redditclonespring.model.Subreddit;

@Mapper(componentModel = "spring")
public interface SubredditMapper {
	
	SubredditMapper INSTANCE = Mappers.getMapper( SubredditMapper.class );

	@Mapping(target = "subredditName", ignore = true)
	@Mapping(target = "postCount", expression = "java(mapPosts(subreddit.getPosts()))")
	SubredditDTO mapSubSubredditToDto(Subreddit subreddit);
	
	default Integer mapPosts(List<Post> postCount) { return postCount.size(); }
	
	
	@Mapping(target = "createdDate", ignore = true)
	@Mapping(target = "user", ignore = true)
	@InheritInverseConfiguration
	@Mapping(target = "posts", ignore = true)
	Subreddit mapDtoToSubreddit(SubredditDTO subredditDto);
}
