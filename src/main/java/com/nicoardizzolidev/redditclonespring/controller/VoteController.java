package com.nicoardizzolidev.redditclonespring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nicoardizzolidev.redditclonespring.dto.VoteDTO;
import com.nicoardizzolidev.redditclonespring.services.VoteService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/votes")
@AllArgsConstructor
public class VoteController {

	private final  VoteService voteService;
	
	@PostMapping
	public ResponseEntity<Void> vote(@RequestBody VoteDTO voteDTO) {
		voteService.vote(voteDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
