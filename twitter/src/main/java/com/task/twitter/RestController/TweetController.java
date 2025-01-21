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

import com.task.twitter.RequestEntityDTO.TweetReplyRequest;
import com.task.twitter.RequestEntityDTO.TweetRequest;
import com.task.twitter.ResponseEntityDTO.DefaultResponse;
import com.task.twitter.Service_Implementation.TweetService_Impl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tweet")
public class TweetController {
	
	// Dependency Injection
    @Autowired
    private TweetService_Impl tweetService_Impl;

    // Add new tweet
    @PostMapping("/new_tweet")
    public ResponseEntity<Map<String, Object>> createNewTweet(@Valid @RequestBody TweetRequest tweetRequest) {
        DefaultResponse defaultResponse = tweetService_Impl.createTweet(tweetRequest);
        return new ResponseEntity<>(defaultResponse.getDefultResponseDemo(), HttpStatus.OK);
    }

    // Find all tweets
    @GetMapping("/find_all_tweets")
    public ResponseEntity<Map<String, Object>> findAllTweets() {
        DefaultResponse defaultResponse = tweetService_Impl.findAllTwits();
        return new ResponseEntity<>(defaultResponse.getDefultResponseDemo(), HttpStatus.OK);
    }

    // Retweet
    @PostMapping("/retweet")
    public ResponseEntity<Map<String, Object>> retweet(Long tweetId) {
        DefaultResponse defaultResponse = tweetService_Impl.retweet(tweetId);
        return new ResponseEntity<>(defaultResponse.getDefultResponseDemo(), HttpStatus.OK);
    }

    // Remove retweet
    @PostMapping("/remove_retweet")
    public ResponseEntity<Map<String, Object>> removeRetweet(Long tweetId) {
        DefaultResponse defaultResponse = tweetService_Impl.removeFromRetweet(tweetId);
        return new ResponseEntity<>(defaultResponse.getDefultResponseDemo(), HttpStatus.OK);
    }

    // Delete tweet by ID
    @PostMapping("/delete_tweet")
    public ResponseEntity<Map<String, Object>> deleteTweet(Long tweetId) {
        DefaultResponse defaultResponse = tweetService_Impl.deleteTweetByld(tweetId);
        return new ResponseEntity<>(defaultResponse.getDefultResponseDemo(), HttpStatus.OK);
    }

    // Create reply to a tweet
    @PostMapping("/reply")
    public ResponseEntity<Map<String, Object>> createReply(@Valid @RequestBody TweetReplyRequest tweetReplyRequest) {
        DefaultResponse defaultResponse = tweetService_Impl.createReply(tweetReplyRequest);
        return new ResponseEntity<>(defaultResponse.getDefultResponseDemo(), HttpStatus.OK);
    }

    // Fetch all user tweets
    @GetMapping("/user_tweets")
    public ResponseEntity<Map<String, Object>> getUserTweets() {
        DefaultResponse defaultResponse = tweetService_Impl.getUserTwit();
        return new ResponseEntity<>(defaultResponse.getDefultResponseDemo(), HttpStatus.OK);
    }

    // Find tweets liked by a user
    @GetMapping("/liked_tweets")
    public ResponseEntity<Map<String, Object>> findByLikesContainsUser() {
        DefaultResponse defaultResponse = tweetService_Impl.findByLikesContainsUser();
        return new ResponseEntity<>(defaultResponse.getDefultResponseDemo(), HttpStatus.OK);
    }
}
