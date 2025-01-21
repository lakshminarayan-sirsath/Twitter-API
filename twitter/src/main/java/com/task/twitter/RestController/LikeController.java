package com.task.twitter.RestController;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.task.twitter.ResponseEntityDTO.DefaultResponse;
import com.task.twitter.Service_Implementation.LikeService_Impl;

@RestController
@RequestMapping("/like")
public class LikeController {

	//Dependency Injection
	@Autowired
	LikeService_Impl likeService_Impl;
	
	// Like or Unlike a Tweet
	@PostMapping("/like_tweet/{id}")
	public ResponseEntity<Map<String, Object>> likeOrUnlikeTweet(@PathVariable("id") Long tweetId) {
	    DefaultResponse defaultResponse = likeService_Impl.likeOrUnlikeTweet(tweetId);
	    return new ResponseEntity<>(defaultResponse.getDefultResponseDemo(), HttpStatus.OK);
	}

	// Get All Likes for a Tweet
	@GetMapping("/all_likes")
	public ResponseEntity<Map<String, Object>> getAllLikes(@PathVariable("id") Long tweetId) {
	    DefaultResponse defaultResponse = likeService_Impl.getAllLikes(tweetId);
	    return new ResponseEntity<>(defaultResponse.getDefultResponseDemo(), HttpStatus.OK);
	}

	
}
