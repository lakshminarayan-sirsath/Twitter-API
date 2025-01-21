package com.task.twitter.Service_Implementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.task.twitter.GlobalException.CustomException;
import com.task.twitter.JpaRepository.TweetRepository;
import com.task.twitter.JpaRepository.UserRepository;
import com.task.twitter.RequestEntityDTO.TweetReplyRequest;
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
	public DefaultResponse retweet(Long tweetld) {
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
		
		Optional<Tweet> tweetOptional = tweetRepository.findById(tweetld);
		if(tweetOptional.isEmpty()) {
			map.put("status: ", false);
			map.put("message: ", "Tweet not found!, invalid tweet id.");
			throw new CustomException(map);
		}
		
		Tweet tweet = tweetOptional.get();
		
		List<User> userList = tweet.getReTweetUsers();
		if(userList == null) {
			userList = new ArrayList<>();
		}
		userList.add(user);
		
		Tweet savedTweet = tweetRepository.save(tweet);
        if(savedTweet == null) {
        	map.put("status: ", false);
			map.put("message: ", "Something wrong, can not retweet!");
			throw new CustomException(map);
        }
        else {
        	map.put("status: ", true);
			map.put("message: ", "retweeted sucessfully.");
			return new DefaultResponse(map);
		}
	}
	
	@Override
	public DefaultResponse removeFromRetweet(Long tweetId) {
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
		
		Optional<Tweet> tweetOptional = tweetRepository.findById(tweetId);
		if(tweetOptional.isEmpty()) {
			map.put("status: ", false);
			map.put("message: ", "Tweet not found!, invalid tweet id.");
			throw new CustomException(map);
		}
		
		Tweet tweet = tweetOptional.get();
		
		List<User> userList = tweet.getReTweetUsers();
		if(userList == null || !userList.contains(user)) {
			map.put("status: ", false);
			map.put("message: ", "User not retweeted this tweet!");
			throw new CustomException(map);
		}
		userList.remove(user);
		
		Tweet savedTweet = tweetRepository.save(tweet);
        if(savedTweet == null) {
        	map.put("status: ", false);
			map.put("message: ", "Something wrong, not removed from retweet!");
			throw new CustomException(map);
        }
        else {
        	map.put("status: ", true);
			map.put("message: ", "removed from retweet sucessfully.");
			return new DefaultResponse(map);
		}
	}
	
	

	@Override
	public DefaultResponse deleteTweetByld(Long tweetId) {
		Authentication authentication = SecurityContextHolder
				.getContext()
				.getAuthentication();
		
		Map<String, Object> map = new HashMap<>();
		
		Tweet tweet = this.findByld(tweetId);
		User user = userRepository.findByEmail(authentication.getName());
		if (user.getId() != tweet.getUser().getId() ) {
			map.put("status: ", false);
			map.put("message: ", "You can not delete another user tweet.");
			throw new CustomException(map);
		}
		else {
			tweetRepository.deleteById(tweetId);
			map.put("status: ", true);
			map.put("message: ", "Tweet deleted successfully.");
			return new DefaultResponse(map); 
		}
	}
	
	// create endpoints
	@Override
	public DefaultResponse createReply(TweetReplyRequest tweetReplyRequest) {
		Authentication authentication = SecurityContextHolder
				.getContext()
				.getAuthentication();
		
		Map<String, Object> map = new HashMap<>();

		Optional<Tweet> replyForOptional = tweetRepository.findById(tweetReplyRequest.getReplyForTweetId());
		if(replyForOptional.isEmpty()) {
			map.put("status: ", false);
			map.put("message: ", "Reply for tweet not found, invalid id!");
			throw new CustomException(map);
		}
		
		Tweet replyForTweet = replyForOptional.get();
		
		User user = userRepository.findByEmail(authentication.getName());
		if(user == null) {
			map.put("status: ", false);
			map.put("message: ", "User not found!");
			throw new CustomException(map);
		}
		
		Tweet tweet = new Tweet();

		tweet.setContent(tweetReplyRequest.getContent());
		tweet.setCreatedAt(LocalDateTime.now());
		tweet.setImage(tweetReplyRequest.getImage());
		tweet.setUser(user);
		tweet.setReply(tweetReplyRequest.isReply());
		tweet.setTweet(tweetReplyRequest.isTweet());
		tweet.setReplyFor(replyForTweet); // tweet(reply) created for main tweet replyFor
		
		Tweet savedReply = tweetRepository.save(tweet);
		if(tweet.getReplyTweets() == null) {
			List<Tweet> replyTweets = new ArrayList<>();
			replyTweets.add(savedReply);
			tweet.setReplyTweets(replyTweets);
		}
		else {
			tweet.getReplyTweets().add(savedReply); // @@@@@@@@@@@@@@@
		}
		
		tweetRepository.save(replyForTweet);

		
		map.put("status: ", true);
		map.put("message: ", "replyed successfully.");
		map.put("reply_for: ", replyForTweet.getContent()); 
		map.put("replyed content: ", tweet.getContent());
		
		return new DefaultResponse(map);
	}
	
	@Override
	public DefaultResponse getUserTwit() {
		Authentication authentication = SecurityContextHolder
				.getContext()
				.getAuthentication();
		Map<String, Object> map = new HashMap<>();
		
		User user = userRepository.findByEmail(authentication.getName());
		
		List<Tweet> tweets = tweetRepository.findAllUserTweets(user.getId());
		if(tweets.isEmpty()) {
			map.put("status: ", false);
			map.put("message: ", "user have empty tweet list.");
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
	public DefaultResponse findByLikesContainsUser() {
		Authentication authentication = SecurityContextHolder
				.getContext()
				.getAuthentication();
		Map<String, Object> map = new HashMap<>();
		
		User user = userRepository.findByEmail(authentication.getName());
		
		List<Tweet> tweets = tweetRepository.findByLikedTweetsByUser(user.getId());
		if(tweets.isEmpty()) {
			map.put("status: ", false);
			map.put("message: ", "user have empty tweet list.");
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
		map.put("message: ", "list of tweet like by user fetched successfully.");
		map.put("tweets: ", tweetResponses);
		
		return new DefaultResponse(map);
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

	// -----------------------------------Service-to-other-method----------------------------------------

	@Override
	public Tweet findByld(Long tweetId) {
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
		return tweet;
//		TweetResponse tweetResponse = new TweetResponse();
//		tweetResponse.setId(tweet.getId());
//		tweetResponse.setEmail(tweet.getUser().getEmail());
//		tweetResponse.setContent(tweet.getContent());
//		tweetResponse.setImage(tweet.getImage());
//		tweetResponse.setVideo(tweet.getVideo());
//		tweetResponse.setReply(tweet.isReply()); // setIsReply getIsReply
//		tweetResponse.setTweet(tweet.isTweet()); // setIsTweet getIsTweet
//		
//		tweetResponse.setLikes_size(tweet.getLikes().size());
//		tweetResponse.setReplyTweets_size(tweet.getReplyTweets().size());
//		tweetResponse.setReTweetUsers_size(tweet.getReTweetUsers().size());
//		map.put("status: ", false);
//		map.put("message: ", "tweet fetched by id successfully.");
//		map.put("tweet: ", tweetResponse);
//		return new DefaultResponse(map);
		
	}


	
}
