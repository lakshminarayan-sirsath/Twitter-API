package com.task.twitter.Service;

import com.task.twitter.RequestEntityDTO.TweetReplyRequest;
import com.task.twitter.RequestEntityDTO.TweetRequest;
import com.task.twitter.ResponseEntityDTO.DefaultResponse;
import com.task.twitter.ResponseEntityDTO.TweetResponse;
import com.task.twitter.Table.Tweet;
import com.task.twitter.Table.User;

public interface TweetService {

	DefaultResponse createTweet(TweetRequest tweetRequest);
	
	DefaultResponse findAllTwits();
	
	DefaultResponse retweet(Long tweetld);
	
	DefaultResponse removeFromRetweet(Long tweetId);
	
	DefaultResponse deleteTweetByld(Long twitld);
	
	DefaultResponse createReply(TweetReplyRequest tweetReplyRequest);
	
	DefaultResponse getUserTwit();
	
	DefaultResponse findByLikesContainsUser();
	
	//------------------------------Tweet-DTO-Methods--------------------------------------
	
	Tweet tweetRequest_To_Tweet(TweetRequest tweetRequest);
	
	TweetResponse Tweet_To_TweetResponse(Tweet tweet);
	
	// -----------------------------------Service-to-other-method----------------------------------------
	
	Tweet findByld(Long tweetld);
	
}
