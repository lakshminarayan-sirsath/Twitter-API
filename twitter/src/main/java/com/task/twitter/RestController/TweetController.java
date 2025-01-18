package com.task.twitter.RestController;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.task.twitter.RequestEntityDTO.TweetRequest;
import com.task.twitter.ResponseEntityDTO.DefaultResponse;
import com.task.twitter.Service_Implementation.TweetService_Impl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tweet")
public class TweetController {

	// Dependency Injection
		@Autowired
		TweetService_Impl tweetService_Impl;
		
		//add new tweet
		@PostMapping("/new_tweet") // save
			public ResponseEntity<Map<String, Object>> createNewTweet(@Valid @RequestBody TweetRequest tweetRequest) {
			DefaultResponse defaultResponse = tweetService_Impl.createTweet(tweetRequest);
			return new ResponseEntity<>(defaultResponse.getDefultResponseDemo(), HttpStatus.OK); 
		}
		
		//find all tweets
		@GetMapping("/find_all_tweets") // save
		public ResponseEntity<Map<String, Object>> createNewTweet() {
		DefaultResponse defaultResponse = tweetService_Impl.findAllTwits();
		return new ResponseEntity<>(defaultResponse.getDefultResponseDemo(), HttpStatus.OK); 
		}
}
