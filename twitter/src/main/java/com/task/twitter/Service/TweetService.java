package com.task.twitter.Service;

import java.util.List;

import com.task.twitter.RequestEntityDTO.TweetRequest;
import com.task.twitter.ResponseEntityDTO.DefaultResponse;
import com.task.twitter.ResponseEntityDTO.TweetResponse;
import com.task.twitter.Table.Tweet;

public interface TweetService {

	DefaultResponse createTweet(TweetRequest tweetRequest);
	
	DefaultResponse findAllTwits();
	
	DefaultResponse createReply();
	
	//------------------------------Tweet-DTO-Methods--------------------------------------
	
	Tweet tweetRequest_To_Tweet(TweetRequest tweetRequest);
	
	TweetResponse Tweet_To_TweetResponse(Tweet tweet);
	
//	List<TweetResponse> list_Tweet_To_TweetResponse(List<Tweet> tweets);
	
}
