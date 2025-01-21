package com.task.twitter.JpaRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.task.twitter.Table.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {
	
	@Query("SELECT l FROM Like l WHERE l.user.id = ?1 AND l.tweet.id = ?2")
	public Like isLikeExist(Long userId, Long tweetId);

	@Query("SELECT l FROM Like l WHERE l.tweet.id = ?1")
	public List<Like> findByTwitId(Long tweetId);

}
