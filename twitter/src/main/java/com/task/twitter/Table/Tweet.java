package com.task.twitter.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tweet")
public class Tweet {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private User user;
	
	private String content;
	
	private String image;
	
	private String video;
	
	@OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL)
	private List<Like> likes = new ArrayList<>();
	
	@OneToMany
	private List<Tweet> replyTweets = new ArrayList<>();
	
	@ManyToMany
	private List<User> reTweetUsers = new ArrayList<>();
	
	@ManyToOne
	private Tweet replyFor;
	
	@Column(columnDefinition = "TINYINT(1)", nullable = false)
	private boolean isReply; // isReply
	
	@Column(columnDefinition = "TINYINT(1)", nullable = false)
	private boolean isTweet; // isTweet
	
	private LocalDateTime createdAt;
	
}
