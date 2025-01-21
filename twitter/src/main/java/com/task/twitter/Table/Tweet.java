package com.task.twitter.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	
	private String content;
	
	private String image;
	
	private String video;
	
	@OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Like> likes = new ArrayList<>();
	
	@OneToMany(mappedBy = "replyFor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Tweet> replyTweets = new ArrayList<>(); // Modified for bidirectional mapping
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.LAZY)
    @JoinTable(
        name = "retweet_users",
        joinColumns = @JoinColumn(name = "tweet_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> reTweetUsers = new ArrayList<>(); // Added JoinTable for Many-to-Many
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "reply_for_id") // Added JoinColumn name for clarity
	private Tweet replyFor;
	
	@Column(columnDefinition = "TINYINT(1)", nullable = false)
	private boolean isReply; // isReply
	
	@Column(columnDefinition = "TINYINT(1)", nullable = false)
	private boolean isTweet; // isTweet
	
	private LocalDateTime createdAt;
	
}
