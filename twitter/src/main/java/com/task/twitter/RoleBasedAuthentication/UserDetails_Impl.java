package com.task.twitter.RoleBasedAuthentication;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.task.twitter.Table.User;

import lombok.AllArgsConstructor;

// UsersDetails
// Represents a data object or model that contains user-related information,
// often used to store and retrieve user data.
//
// Example Use:
// Holds fields like username, password, roles, etc.
// Used in authentication or user profile management.

@AllArgsConstructor
public class UserDetails_Impl implements UserDetails {
	
	private User user;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	    // Fetch roles through the user object and map them to GrantedAuthority
	    return user.getRoles().stream()
	        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole().name())) 
	        .collect(Collectors.toList());
	}
	
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}


}
