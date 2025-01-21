package com.task.twitter.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String fullName;
	
	private String location;
	
	private String website;
	
	private String birthDate;
	
	private String email;
	
	private String password;
	
	private String mobile;
	
	private String image;
	
	private String backgroundImage;

	private String bio;

	private boolean reqUser;

	private boolean loginWithGoogle;
	

	@ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
	@JoinTable(
	    name = "user_role",
	    joinColumns = @JoinColumn(name = "user_id"),
	    inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	private Set<Role> roles;
	
	
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Tweet> tweets = new ArrayList<>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Like> likes=new ArrayList<>();
	
	@Embedded
	private Varification verification;
	
	 // Many-to-Many for Followers
    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "user_followers", 
        joinColumns = @JoinColumn(name = "following_id"), // Current user is following
        inverseJoinColumns = @JoinColumn(name = "follower_id") // Users following the current user
    )
    private List<User> followers = new ArrayList<>();

    // Many-to-Many for Following
    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "user_following", 
        joinColumns = @JoinColumn(name = "follower_id"), // Current user is the follower
        inverseJoinColumns = @JoinColumn(name = "following_id") // Users the current user is following
    )
    private List<User> following = new ArrayList<>();
	
}
