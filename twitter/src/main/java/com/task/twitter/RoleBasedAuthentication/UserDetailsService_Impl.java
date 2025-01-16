package com.task.twitter.RoleBasedAuthentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.task.twitter.JpaRepository.UserRepository;
import com.task.twitter.Table.User;

//	UserDetailsService
//	A service interface in Spring Security that provides
//	a way to fetch user details from a data source (e.g., database).
//	
//	Example Use:
//	Loads UsersDetails by username for authentication.
//	Implemented to integrate custom user retrieval logic.

@Service
public class UserDetailsService_Impl implements UserDetailsService{
	
	// Dependency Injection
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email); // find user using user email
		
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        
        return new UserDetails_Impl(user);
	}

}
