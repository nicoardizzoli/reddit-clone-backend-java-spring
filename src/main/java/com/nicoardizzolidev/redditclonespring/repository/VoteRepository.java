package com.nicoardizzolidev.redditclonespring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nicoardizzolidev.redditclonespring.model.Post;
import com.nicoardizzolidev.redditclonespring.model.User;
import com.nicoardizzolidev.redditclonespring.model.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long>{

	Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);

}
