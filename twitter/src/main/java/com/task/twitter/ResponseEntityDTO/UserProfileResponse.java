package com.task.twitter.ResponseEntityDTO;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileResponse {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private String fullName;
	
	private String location;
	
	private String website;
	
	private String birthDate;
	
	private String email;
	
	private String mobile;
	
	private String image;
	
	private String backgroundImage;

	private String bio;
	
	private int tweet_size;
	
	private int likes_size;
	
//	private Varification verification;
	
	private int followers_size;
	
	private int following_size;
	
}
