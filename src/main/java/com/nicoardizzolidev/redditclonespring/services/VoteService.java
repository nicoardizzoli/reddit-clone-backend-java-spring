package com.nicoardizzolidev.redditclonespring.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nicoardizzolidev.redditclonespring.dto.VoteDTO;
import com.nicoardizzolidev.redditclonespring.exceptions.PostNotFoundException;
import com.nicoardizzolidev.redditclonespring.exceptions.SpringRedditException;
import com.nicoardizzolidev.redditclonespring.model.Post;
import com.nicoardizzolidev.redditclonespring.model.Vote;
import com.nicoardizzolidev.redditclonespring.model.VoteType;
import com.nicoardizzolidev.redditclonespring.repository.PostRepository;
import com.nicoardizzolidev.redditclonespring.repository.VoteRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VoteService {
	
	private final VoteRepository voteRepository;
	private final PostRepository postRepository;
	private final AuthService authService;
	
	
	public void vote(VoteDTO voteDTO) {
		Post post = postRepository.findById(voteDTO.getPostId()).orElseThrow(() -> new PostNotFoundException("post no encontrado id: " + voteDTO.getPostId()));
		Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
		if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDTO.getVoteType())) {
			throw new SpringRedditException("You have already" + voteDTO.getVoteType() + " 'd for this post");
		}
		
		if (VoteType.UPVOTE.equals(voteDTO.getVoteType())) {
			post.setVoteCount(post.getVoteCount() + 1);
		} else {
			post.setVoteCount(post.getVoteCount() - 1);
		}
		
		Vote vote = mapToVote(voteDTO, post);
		voteRepository.save(vote);
		postRepository.save(post);
	}
	
	private Vote mapToVote(VoteDTO voteDTO, Post post) {
		return Vote.builder()
				.voteType(voteDTO.getVoteType())
				.post(post)
				.user(authService.getCurrentUser())
				.build();
	}

}
