package com.task.twitter.Service_Implementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.task.twitter.GlobalException.CustomException;
import com.task.twitter.JpaRepository.TweetRepository;
import com.task.twitter.JpaRepository.UserRepository;
import com.task.twitter.RequestEntityDTO.TweetRequest;
import com.task.twitter.ResponseEntityDTO.DefaultResponse;
import com.task.twitter.ResponseEntityDTO.TweetResponse;
import com.task.twitter.Service.TweetService;
import com.task.twitter.Table.Tweet;
import com.task.twitter.Table.User;

@Service
public class TweetService_Impl implements TweetService{
	
	// Dependency Injection
	@Autowired
	TweetRepository tweetRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ModelMapper modelMapper;


	@Override
	public DefaultResponse createTweet(TweetRequest tweetRequest) {
		Authentication authentication = SecurityContextHolder
				.getContext()
				.getAuthentication();
		
		Map<String, Object> map = new HashMap<>();
		
		User user = userRepository.findByEmail(authentication.getName());
		if(user == null) {
			map.put("status: ", false);
			map.put("message: ", "User not found!");
			throw new CustomException(map);
		}
		
		Tweet tweet = this.tweetRequest_To_Tweet(tweetRequest);
		tweet.setTweet(tweetRequest.isTweet()); // Debugging @@@@@@@
		tweet.setReply(tweetRequest.isReply()); // Debugging @@@@@@@ 
		tweet.setCreatedAt(LocalDateTime.now()); 
		tweet.setUser(user);
		
		// associate (bi-dir)
		List<Tweet> tweets = user.getTweets();
        if (tweets == null) {
            tweets = new ArrayList<>(); 
            tweets.add(tweet);
            user.setTweets(tweets); 
        }
        
        Tweet savedTweet = tweetRepository.save(tweet);
        if(savedTweet == null) {
        	map.put("status: ", false);
			map.put("message: ", "Something wrong, tweet not saved!");
			throw new CustomException(map);
        }
        else {
        	map.put("status: ", true);
			map.put("message: ", "Tweet created(saved) sucessfully.");
			return new DefaultResponse(map);
		}
				
	}
	
	@Override
	public DefaultResponse findAllTwits() {
		Authentication authentication = SecurityContextHolder
				.getContext()
				.getAuthentication();
		
		Map<String, Object> map = new HashMap<>();
		
		List<Tweet> tweets = tweetRepository.findAllOrderByCreatedAtAsc();
		if(tweets == null) {
			map.put("status: ", false);
			map.put("message: ", "Something wrong or no tweets available!");
			throw new CustomException(map);
		}
		
		List<TweetResponse> tweetResponses = new ArrayList<>();
		for(Tweet tmpTweet: tweets) {
//			TweetResponse tweetResponse = this.Tweet_To_TweetResponse(tmpTweet); // using modelMappper
			TweetResponse tweetResponse = new TweetResponse();
			tweetResponse.setId(tmpTweet.getId());
			tweetResponse.setEmail(tmpTweet.getUser().getEmail());
			tweetResponse.setContent(tmpTweet.getContent());
			tweetResponse.setImage(tmpTweet.getImage());
			tweetResponse.setVideo(tmpTweet.getVideo());
			tweetResponse.setReply(tmpTweet.isReply()); // setIsReply getIsReply
			tweetResponse.setTweet(tmpTweet.isTweet()); // setIsTweet getIsTweet
			
			tweetResponse.setLikes_size(tmpTweet.getLikes().size());
			tweetResponse.setReplyTweets_size(tmpTweet.getReplyTweets().size());
			tweetResponse.setReTweetUsers_size(tmpTweet.getReTweetUsers().size());
			
			tweetResponses.add(tweetResponse);
		}
		
		map.put("status: ", true);
		map.put("message: ", "list of tweet fetched successfully.");
		map.put("tweets: ", tweetResponses);
		
		return new DefaultResponse(map);
	}
	
	@Override
	public DefaultResponse createReply() {
		// TODO Auto-generated method stub
		return null;
	}
	
	//------------------------------Tweet-DTO-Methods--------------------------------------
	
	@Override
	public Tweet tweetRequest_To_Tweet(TweetRequest tweetRequest) {
		Tweet tweet = modelMapper.map(tweetRequest, Tweet.class);
		return tweet;
	}
	
	@Override
	public TweetResponse Tweet_To_TweetResponse(Tweet tweet) {
		TweetResponse tweetResponse = modelMapper.map(tweet, TweetResponse.class);
		return tweetResponse;
	}


//	@Override
//	public List<TweetResponse> list_Tweet_To_TweetResponse(List<Tweet> tweets) {
//		List<TweetResponse> tweetResponses = new ArrayList<>();
//		for(Tweet tmpTweet: tweets) {
//			TweetResponse tweetResponse = modelMapper.map(tmpTweet, TweetResponse.class);
//			tweetResponses.add(tweetResponse);
//		}
//		return tweetResponses;
//	}

	

}
