package com.task.twitter.JpaRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.task.twitter.Table.Tweet;

public interface TweetRepository extends JpaRepository<Tweet, Long> {
 
	@Query("SELECT t FROM Tweet t ORDER BY t.createdAt ASC")
	List<Tweet> findAllOrderByCreatedAtAsc();

}
