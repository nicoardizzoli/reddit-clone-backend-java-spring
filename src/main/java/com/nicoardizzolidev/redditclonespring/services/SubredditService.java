package com.nicoardizzolidev.redditclonespring.services;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nicoardizzolidev.redditclonespring.dto.SubredditDTO;
import com.nicoardizzolidev.redditclonespring.exceptions.SpringRedditException;
import com.nicoardizzolidev.redditclonespring.mapper.SubredditMapper;
import com.nicoardizzolidev.redditclonespring.model.Subreddit;
import com.nicoardizzolidev.redditclonespring.repository.SubredditRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

	private final SubredditRepository subredditRepository;
	
	@Transactional
	public SubredditDTO save(SubredditDTO subredditDto) {
		Subreddit savedSubredit = subredditRepository.save(SubredditMapper.INSTANCE.mapDtoToSubreddit(subredditDto));
		subredditDto.setId(savedSubredit.getId());
		return subredditDto;
	}

//	private Subreddit mapSubredditDto(SubredditDto subredditDto) {
//		return Subreddit.builder()
//					.name(subredditDto.getName())
//					.description(subredditDto.getDescription())
//					.build();
//		
//	}
	
	@Transactional(readOnly = true)
	public List<SubredditDTO> getAll() {
		return subredditRepository.findAll().stream().map(SubredditMapper.INSTANCE::mapSubSubredditToDto).collect(Collectors.toList());
	}

	public SubredditDTO getSubreddit(Long id) {
		Subreddit subredditRecuperado = subredditRepository.findById(id)
				.orElseThrow(() -> new SpringRedditException("no subreddit found with id " + id));
		
		return SubredditMapper.INSTANCE.mapSubSubredditToDto(subredditRecuperado);
	}

//	private SubredditDto mapToDto(Subreddit subreddit1) {
//		return SubredditDto.builder()
//				.name(subreddit1.getName())
//				.id(subreddit1.getId())
//				.postCount(subreddit1.getPosts().size())
//				.build();
//	}
}
