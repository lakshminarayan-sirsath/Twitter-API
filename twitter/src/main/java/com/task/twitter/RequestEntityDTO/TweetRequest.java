package com.task.twitter.RequestEntityDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TweetRequest {

	@NotBlank(message = "content cannot be blank.")
	private String content;
	
	private String image;
	
	private String video;
	
	private boolean reply;
	
	private boolean tweet;	
	
}
