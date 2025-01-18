package com.task.twitter.ResponseEntityDTO;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TweetResponse {

	private Long id;

	private String email; // User-name
	
	private String content;
	
	private String image;
	
	private String video;
	
	private int likes_size;
	
	private int replyTweets_size;
	
	private int reTweetUsers_size;

//	private Tweet replyFor;
	
	private boolean reply; // isReply
	
	private boolean tweet; // isTweet
	
	private LocalDateTime createdAt;
	
	
	
}
