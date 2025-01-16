package com.task.twitter.Service_Implementation;

import java.util.HashMap;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.task.twitter.GlobalException.CustomException;
import com.task.twitter.JWT_UtilityAndFilter.JwtUtil;
import com.task.twitter.JpaRepository.UserRepository;
import com.task.twitter.RequestEntityDTO.UserLoginRequest;
import com.task.twitter.RequestEntityDTO.UserRequest;
import com.task.twitter.ResponseEntityDTO.DefaultResponse;
import com.task.twitter.Service.UserService;
import com.task.twitter.Table.User;

@Service
public class UserService_impl implements UserService{
	
	// Dependency Injection
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public DefaultResponse createNewUser(UserRequest userRequest) {
		Map<String, Object> map = new HashMap<>();
//		
//		if(userRepository.findByEmail(userRequest.getEmail()) != null) {
//			map.put("status: ", false);
//			map.put("message: ", "user already exist. (check by email)");
//			throw new CustomException(map);
//		}
		
		User user = this.userRequest_To_User(userRequest);
		user.setPassword(passwordEncoder.encode(user.getPassword())); // Encode password
		User savedUser = userRepository.save(user);
		
		if(savedUser == null) {
			map.put("status: ", false);
			map.put("message: ", "Something wrong, user not saved.");
			throw new CustomException(map);
		}
		else {
			map.put("status: ", true);
			map.put("message: ", "User saved(created) successfully.");
			return new DefaultResponse(map);
		}
	}
	
	@Override
	public DefaultResponse userLogin(UserLoginRequest userLoginRequest) {
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> claims = new HashMap<>();
		
		try {
			
			Authentication authentication = authenticationManager.authenticate(
			          new UsernamePasswordAuthenticationToken(
			        		  userLoginRequest.getEmail(), // User-name
			        		  userLoginRequest.getPassword())
			          );

			String jwt = jwtUtil.generateToken(claims, userLoginRequest.getEmail()); // claim is empty, email is User-name
			map.put("status: ", true);
			map.put("message: ", "login success.(valid user)");
			map.put("jwt token: ", jwt);
					
			return new DefaultResponse(map);
			
		} catch (BadCredentialsException e) {
			map.put("status: ", false);
			map.put("message: ", e.getMessage()+" (exception)");
			throw new CustomException(map);
		}
	}
	
	//------------------------------User-DTO-Methods--------------------------------------
	
	@Override
	public User userRequest_To_User(UserRequest userRequest) {
		User user = modelMapper.map(userRequest, User.class);
		return user;
	}

}
