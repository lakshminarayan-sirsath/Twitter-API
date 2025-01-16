package com.task.twitter.RestController;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
	//add new book
	@PostMapping("/new_user")
		public ResponseEntity<Map<String, Object>> save(@Valid @RequestBody UserRequest userRequest) {
		DefaultResponse defaultResponse = userService_impl.createNewUser(userRequest);
		return new ResponseEntity<>(defaultResponse.getDefultResponseDemo(), HttpStatus.OK); 
	}
	
	//add new book
	@GetMapping("/login")
		public ResponseEntity<Map<String, Object>> save(@Valid @RequestBody UserLoginRequest userLoginRequest) {
		DefaultResponse defaultResponse = userService_impl.userLogin(userLoginRequest);
		return new ResponseEntity<>(defaultResponse.getDefultResponseDemo(), HttpStatus.OK); 
	}

	
}
