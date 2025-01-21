package com.task.twitter.Service_Implementation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.task.twitter.GlobalException.CustomException;
import com.task.twitter.JpaRepository.LikeRepository;
import com.task.twitter.JpaRepository.TweetRepository;
import com.task.twitter.JpaRepository.UserRepository;
import com.task.twitter.ResponseEntityDTO.DefaultResponse;
import com.task.twitter.Service.LikeService;
import com.task.twitter.Table.Like;
import com.task.twitter.Table.Tweet;
import com.task.twitter.Table.User;

@Service
public class LikeService_Impl implements LikeService {
	
	// Dependency injection
	@Autowired
	LikeRepository likeRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	TweetRepository tweetRepository;

	@Override
	public DefaultResponse likeOrUnlikeTweet(Long tweetld) {
		Authentication authentication = SecurityContextHolder
				.getContext()
				.getAuthentication();
		
		Map<String, Object> map = new HashMap<>();
		
		User user = userRepository.findByEmail(authentication.getName());
		Optional<Tweet> tweetOptional = tweetRepository.findById(tweetld);
		if(tweetOptional.isEmpty()) {
			map.put("status: ", false);
			map.put("message: ", "Tweet not found!, invalid tweet id.");
			throw new CustomException(map);
		}
		
		Tweet tweet = tweetOptional.get();
		
		Like isLikeExist = likeRepository.isLikeExist(tweetld, user.getId());
		
		if(isLikeExist != null) {
			likeRepository.deleteById(isLikeExist.getId());
			map.put("status: ", true);
			map.put("message: ", "removed like from tweet by user.");
			return new DefaultResponse(map);
		}
		
		Like like = new Like();
		like.setTweet(tweet);
		like.setUser(user);
		
		Like savedLike=likeRepository.save(like);
		tweet.getLikes().add(savedLike);	

		if(savedLike ==null) {
			map.put("status: ", false);
			map.put("message: ", "can't like to tweet, error.");
			throw new CustomException(map);
		}
		else {
			map.put("status: ", true);
			map.put("message: ", "tweet liked by user.");
			return new DefaultResponse(map);
		}
		
	}

	@Override
	public DefaultResponse getAllLikes(Long tweetId) {
		
		Authentication authentication = SecurityContextHolder
				.getContext()
				.getAuthentication();
		Map<String, Object> map = new HashMap<>();
		
		Optional<Tweet> tweetOptional = tweetRepository.findById(tweetId);
		if(tweetOptional.isEmpty()) {
			map.put("status: ", false);
			map.put("message: ", "Tweet not found!, invalid tweet id.");
			throw new CustomException(map);
		}
		Tweet tweet = tweetOptional.get();
		
		List<Like> likes = likeRepository.findByTwitId(tweetId);
		
		map.put("status: ", true);
		map.put("message: ", "like fetched successfully.");
		return new DefaultResponse(map);		
	}

}
