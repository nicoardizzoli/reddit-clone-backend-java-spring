package com.nicoardizzolidev.redditclonespring.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nicoardizzolidev.redditclonespring.dto.PostRequestDTO;
import com.nicoardizzolidev.redditclonespring.dto.PostResponseDTO;
import com.nicoardizzolidev.redditclonespring.exceptions.PostNotFoundException;
import com.nicoardizzolidev.redditclonespring.exceptions.SubredditNotFoundException;
import com.nicoardizzolidev.redditclonespring.mapper.PostMapper;
import com.nicoardizzolidev.redditclonespring.model.Post;
import com.nicoardizzolidev.redditclonespring.model.Subreddit;
import com.nicoardizzolidev.redditclonespring.model.User;
import com.nicoardizzolidev.redditclonespring.repository.PostRepository;
import com.nicoardizzolidev.redditclonespring.repository.SubredditRepository;
import com.nicoardizzolidev.redditclonespring.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class PostService {

	private final SubredditRepository subredditRepository;
	private final AuthService authService;
	private final PostMapper postMapper;
	private final PostRepository postRepository;
	private final UserRepository userRepository;

	@Transactional
	public Post save(PostRequestDTO postRequestDTO) {
		Subreddit subreddit = subredditRepository.findByName(postRequestDTO.getSubredditName())
				.orElseThrow(() -> new SubredditNotFoundException(postRequestDTO.getSubredditName()));
		User currentUser = authService.getCurrentUser();

		Post post = postMapper.map(postRequestDTO, subreddit, currentUser);
		
		postRepository.save(post);

		return post;
	}

	@Transactional
	public PostResponseDTO getPost(Long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id.toString()));

		PostResponseDTO postResponseDTO = postMapper.mapToDto(post);

		return postResponseDTO;
	}
	
	@Transactional
	public List<PostResponseDTO> getAllPosts() {
		return postRepository.findAll().stream().map(postMapper::mapToDto).collect(Collectors.toList());
	}
	
	@Transactional
	public List<PostResponseDTO> getPostsBySubreddit(Long subredditId) {
		
		Subreddit subreddit = subredditRepository.findById(subredditId).orElseThrow(()-> new SubredditNotFoundException(subredditId.toString()));
		
		return postRepository.findAllBySubreddit(subreddit).stream().map(postMapper::mapToDto).collect(Collectors.toList());
	}
	
	
	@Transactional
	public List<PostResponseDTO> getPostsByUsername(String username) {
		
		User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
		
		return postRepository.findAllByUser(user).stream().map(postMapper::mapToDto).collect(Collectors.toList());
	}
}
