package com.task.twitter.Table;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "twit")
public class Twit {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long twit_id;
	
	@ManyToOne
	private User user;
	
	private String content;
	
	private String image;
	
	private String video;
	
	@OneToMany(mappedBy = "twit", cascade = CascadeType.ALL)
	private List<Like> likes = new ArrayList<>();
	
	@OneToMany
	private List<Twit> replyTwits = new ArrayList<>();
	
	@ManyToMany
	private List<User> retwitUse = new ArrayList<>();
	
	@ManyToOne
	private Twit replyFor;
	
	private boolean isTwit;	
	
}
