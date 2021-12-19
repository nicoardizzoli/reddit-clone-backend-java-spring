package com.nicoardizzolidev.redditclonespring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubredditDTO {
	private Long id;
	private String subredditName;
	private String description;
	private Integer postCount;
	private String name;
	

}
