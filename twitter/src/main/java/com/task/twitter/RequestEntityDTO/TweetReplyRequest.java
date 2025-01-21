package com.task.twitter.RequestEntityDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TweetReplyRequest {
	
	private Long replyForTweetId; // reply for this tweet (find by id)
	
	private String content;
	
	private String image;
	
//	private String video;s
	
	private boolean reply;
	
	private boolean tweet;	
}
