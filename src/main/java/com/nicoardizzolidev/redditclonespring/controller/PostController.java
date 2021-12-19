package com.nicoardizzolidev.redditclonespring.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nicoardizzolidev.redditclonespring.dto.PostRequestDTO;
import com.nicoardizzolidev.redditclonespring.dto.PostResponseDTO;
import com.nicoardizzolidev.redditclonespring.services.PostService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {

	private final PostService postService;

	@PostMapping
	public ResponseEntity<Void> createPost(@RequestBody PostRequestDTO postRequestDTO) {

		postService.save(postRequestDTO);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<PostResponseDTO>> getAllPosts(){
		return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPosts());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PostResponseDTO> getPost(@PathVariable Long id){
		return ResponseEntity.status(HttpStatus.OK).body(postService.getPost(id));
	}
	
	@GetMapping("/by-subreddit/{id}")
	public ResponseEntity<List<PostResponseDTO>> getPostsBySubreddit(@PathVariable Long id){
		return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsBySubreddit(id));
	}
	
	@GetMapping("/by-subreddit/{username}")
	public ResponseEntity<List<PostResponseDTO>> getPostsByUsername(@PathVariable String username){
		return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsByUsername(username));
	}

}
