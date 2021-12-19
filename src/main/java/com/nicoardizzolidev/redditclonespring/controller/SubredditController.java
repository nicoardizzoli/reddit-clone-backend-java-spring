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

import com.nicoardizzolidev.redditclonespring.dto.SubredditDTO;
import com.nicoardizzolidev.redditclonespring.services.SubredditService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
@Slf4j
public class SubredditController {
	
	private final SubredditService subredditService;
	
	@PostMapping
	public ResponseEntity<SubredditDTO> createSubreddit(@RequestBody SubredditDTO subreditDto) {
		SubredditDTO subreditSaved = subredditService.save(subreditDto);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(subreditSaved);
	}
	
	
	@GetMapping
	public ResponseEntity<List<SubredditDTO>> getAllSubreddits() {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(subredditService.getAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<SubredditDTO> getSubredditPorId(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(subredditService.getSubreddit(id));
	}

}
