package com.nicoardizzolidev.redditclonespring.dto;

import com.nicoardizzolidev.redditclonespring.model.VoteType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteDTO {
	private VoteType voteType;
	private Long postId;
}
