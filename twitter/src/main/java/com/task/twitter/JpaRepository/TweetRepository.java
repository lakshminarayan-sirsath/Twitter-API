package com.task.twitter.JpaRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.task.twitter.Table.Tweet;

public interface TweetRepository extends JpaRepository<Tweet, Long> {
 
	@Query("SELECT t FROM Tweet t ORDER BY t.createdAt ASC")
	List<Tweet> findAllOrderByCreatedAtAsc();

//	@Query("SELECT t FROM Tweet t WHERE (" +
//		       "t.user.id = ?1 OR " +
//		       "?1 MEMBER OF t.reTweetUsers) AND " +
//		       "t.isTweet = TRUE " +
//		       "ORDER BY t.createdAt DESC")
//	List<Tweet> findAllUserTweets(Long userId);
	@Query("SELECT t FROM Tweet t WHERE t.user.id = ?1 AND t.isTweet = true ORDER BY t.createdAt DESC")
	List<Tweet> findAllUserTweets(Long userId);


	
	@Query("SELECT t FROM Tweet t JOIN Like l ON l.tweet.id = t.id WHERE l.user.id = ?1")
	List<Tweet> findByLikedTweetsByUser(Long userId);




}
