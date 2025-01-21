package com.task.twitter.Service;

import com.task.twitter.ResponseEntityDTO.DefaultResponse;

public interface LikeService {

	DefaultResponse likeOrUnlikeTweet(Long twitld);
	
	DefaultResponse getAllLikes(Long twitld);
}
