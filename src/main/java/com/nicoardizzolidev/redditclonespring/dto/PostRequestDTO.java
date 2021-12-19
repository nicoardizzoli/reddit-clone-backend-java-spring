package com.nicoardizzolidev.redditclonespring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDTO {
	private Long postId;
	private String subredditName;
	private String postName;
	private String url;
	private String description;
	private Integer voteCount;
}
