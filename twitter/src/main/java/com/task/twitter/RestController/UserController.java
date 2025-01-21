package com.task.twitter.RestController;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.task.twitter.RequestEntityDTO.UpdateUserProfileRequest;
import com.task.twitter.RequestEntityDTO.UserLoginRequest;
import com.task.twitter.RequestEntityDTO.UserRequest;
import com.task.twitter.ResponseEntityDTO.DefaultResponse;
import com.task.twitter.Service_Implementation.UserService_impl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

	// Dependency Injection
	@Autowired
	UserService_impl userService_impl;
	
	//add new user
	@PostMapping("/new_user")
		public ResponseEntity<Map<String, Object>> createUser(@Valid @RequestBody UserRequest userRequest) {
		DefaultResponse defaultResponse = userService_impl.createNewUser(userRequest);
		return new ResponseEntity<>(defaultResponse.getDefultResponseDemo(), HttpStatus.OK); 
	}
	
	// login
	@PostMapping("/login")
		public ResponseEntity<Map<String, Object>> userLogin(@Valid @RequestBody UserLoginRequest userLoginRequest) {
		DefaultResponse defaultResponse = userService_impl.userLogin(userLoginRequest);
		return new ResponseEntity<>(defaultResponse.getDefultResponseDemo(), HttpStatus.OK); 
	}
	
	// update user details
	@PutMapping("/update_user")
	public ResponseEntity<Map<String, Object>> updateUser(@Valid @RequestBody UpdateUserProfileRequest updateUserProfileRequest) {
	DefaultResponse defaultResponse = userService_impl.updateUserProfile(updateUserProfileRequest);
	return new ResponseEntity<>(defaultResponse.getDefultResponseDemo(), HttpStatus.OK); 
	}
	
	// get profile
	@PutMapping("/user_profile")
	public ResponseEntity<Map<String, Object>> userProfile() {
	DefaultResponse defaultResponse = userService_impl.findUserProfile();
	return new ResponseEntity<>(defaultResponse.getDefultResponseDemo(), HttpStatus.OK); 
	}
	
	
	// check many to many table issue. following and followers. @@@@@@@@@@@@@@@@@@@@
	// get profile
	@PostMapping("/follow_user/{id}")
	public ResponseEntity<Map<String, Object>> userProfile(@PathVariable("id") Long userId) {
	DefaultResponse defaultResponse = userService_impl.followUser(userId);
	return new ResponseEntity<>(defaultResponse.getDefultResponseDemo(), HttpStatus.OK); 
	}
	
	
	
}
