package com.task.twitter.Service_Implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.task.twitter.GlobalException.CustomException;
import com.task.twitter.JWT_UtilityAndFilter.JwtUtil;
import com.task.twitter.JpaRepository.RoleRepository;
import com.task.twitter.JpaRepository.UserRepository;
import com.task.twitter.RequestEntityDTO.UpdateUserProfileRequest;
import com.task.twitter.RequestEntityDTO.UserLoginRequest;
import com.task.twitter.RequestEntityDTO.UserRequest;
import com.task.twitter.ResponseEntityDTO.DefaultResponse;
import com.task.twitter.ResponseEntityDTO.UserProfileResponse;
import com.task.twitter.RoleBasedAuthentication.ValidRole;
import com.task.twitter.Service.UserService;
import com.task.twitter.Table.Role;
import com.task.twitter.Table.User;

import jakarta.transaction.Transactional;

@Service
public class UserService_impl implements UserService{
	
	// Dependency Injection
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public DefaultResponse createNewUser(UserRequest userRequest) {
		Map<String, Object> map = new HashMap<>();
		
		if(userRepository.findByEmail(userRequest.getEmail()) != null) {
			map.put("status: ", false);
			map.put("message: ", "user already exist. (check by email)");
			throw new CustomException(map);
		}
		
		User user = this.userRequest_To_User(userRequest);
		user.setPassword(passwordEncoder.encode(user.getPassword())); // Encode password
		
		Set<Role> roles = new HashSet<>();
		
		if(userRequest.getRoles() != null) {
			for(String tmp: userRequest.getRoles()) {
				Role role = roleRepository.findByRole(ValidRole.valueOf(tmp.toUpperCase()));
				if(role == null) {
					map.put("status: ", false);
					map.put("message: ", "invalid user role: "+tmp);
					throw new CustomException(map);
				}
				roles.add(role);
			}
			
			user.setRoles(roles);
		}
		else {
			Role role = roleRepository.findByRole(ValidRole.USER);
			roles.add(role);
			user.setRoles(roles);
		}
		
		
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
	

	@Override
	public DefaultResponse updateUserProfile(UpdateUserProfileRequest updateUserProfileRequest) {
		Authentication authentication = SecurityContextHolder
				.getContext()
				.getAuthentication();
		
		Map<String, Object> map = new HashMap<>();
		
		User user = userRepository.findByEmail(authentication.getName());
		if(user == null) {
			map.put("status: ", false);
			map.put("message: ", "User not found!");
			throw new CustomException(map);
		}
		
		if(updateUserProfileRequest.getFullName() != null) {
			user.setFullName(updateUserProfileRequest.getFullName());
		}
		
		if(updateUserProfileRequest.getLocation() != null) {
			user.setLocation(updateUserProfileRequest.getLocation());
		}
		
		if(updateUserProfileRequest.getWebsite() != null) {
			user.setWebsite(updateUserProfileRequest.getWebsite());
		}
		
		if(updateUserProfileRequest.getBirthDate() != null) {
			user.setBirthDate(updateUserProfileRequest.getBirthDate());
		}
		
		if(updateUserProfileRequest.getMobile() != null) {
			user.setMobile(updateUserProfileRequest.getMobile());
		}
		
		if(updateUserProfileRequest.getImage() != null) {
			user.setImage(updateUserProfileRequest.getImage()); // image
		}
		
		if(updateUserProfileRequest.getBackgroundImage() != null) {
			user.setBackgroundImage(updateUserProfileRequest.getBackgroundImage());
		}
		
		if(updateUserProfileRequest.getBio() != null) {
			user.setBio(updateUserProfileRequest.getBio());
		}
		
		User savedUser = userRepository.save(user);
		if(savedUser == null) {
			map.put("status: ", false);
			map.put("message: ", "Something wrong, user not updated!");
			throw new CustomException(map);
		}
		else {
			map.put("status: ", false);
			map.put("message: ", "User updated successfully!");
			return new DefaultResponse(map);
		}
	}
	
	@Override
	public User findUserById(Long userId) {
		Authentication authentication = SecurityContextHolder
				.getContext()
				.getAuthentication();
		
		Map<String, Object> map = new HashMap<>();
		
		Optional<User> userOptional = userRepository.findById(userId);
		if(userOptional.isEmpty()) {
			map.put("status: ", false);
			map.put("message: ", "User not found!, invalidId");
			throw new CustomException(map);
		}
		return userOptional.get();
	}
	
	@Override
	public DefaultResponse findUserProfile() {
		Authentication authentication = SecurityContextHolder
				.getContext()
				.getAuthentication();
		
		Map<String, Object> map = new HashMap<>();
		
		User user = userRepository.findByEmail(authentication.getName());
		if(user == null) {
			map.put("status: ", false);
			map.put("message: ", "User not found!");
			throw new CustomException(map);
		}
		
		UserProfileResponse userProfileResponse = new UserProfileResponse();
		
		userProfileResponse.setFullName(user.getFullName());
		userProfileResponse.setLocation(user.getLocation());
		userProfileResponse.setWebsite(user.getWebsite());
		userProfileResponse.setBirthDate(user.getBirthDate());
		userProfileResponse.setEmail(user.getEmail());
		userProfileResponse.setMobile(user.getMobile());
		userProfileResponse.setBackgroundImage(user.getBackgroundImage());
		userProfileResponse.setBio(user.getBio());
		userProfileResponse.setTweet_size(user.getTweets().size());
		userProfileResponse.setLikes_size(user.getLikes().size());
		userProfileResponse.setFollowers_size(user.getFollowers().size());
		userProfileResponse.setFollowing_size(user.getFollowing().size());
		
		map.put("status: ", true);
		map.put("message: ", "User Profile fetched successfully!");
		map.put("profile: ", userProfileResponse);
		return new DefaultResponse(map);
	}
	
	@Transactional
	@Override
	public DefaultResponse followUser(Long userld) {
		Authentication authentication = SecurityContextHolder
				.getContext()
				.getAuthentication();
		
		Map<String, Object> map = new HashMap<>();
		
		User user = userRepository.findByEmail(authentication.getName());
		if(user == null) {
			map.put("status: ", false);
			map.put("message: ", "User not found!");
			throw new CustomException(map);
		}
		
		Optional<User> followToUserOptional = userRepository.findById(userld);
		if(followToUserOptional.isEmpty()) {
			map.put("status: ", false);
			map.put("message: ", "User not found, invalid UserId!");
			throw new CustomException(map);
		}
		User followUser = followToUserOptional.get();
		
		// follow
		if(followUser.getFollowers() == null) {
			List<User> followrsList = new ArrayList<>();
			followrsList.add(user);
		}
		else {
			followUser.getFollowers().add(user);
		}
		
		// increase following(associate)
		if(user.getFollowing() == null) {
			List<User> followingList = new ArrayList<>();
			followingList.add(followUser);
		}
		else {
			user.getFollowing().add(followUser);
		}
		
		User savedUser = userRepository.save(user); // save user and followUser (associate)
		if(savedUser == null) {
			map.put("status: ", false);
			map.put("message: ", "following to user unsuccessful! "+followUser.getFullName());
			throw new CustomException(map);
		}
		else {
			map.put("status: ", true);
			map.put("message: ", "followed to user ("+followUser.getFullName()+").");
			return new DefaultResponse(map);
		}
	}

	@Transactional
	@Override
	public DefaultResponse unFollowUser(Long userId) {
	    Authentication authentication = SecurityContextHolder
	            .getContext()
	            .getAuthentication();

	    Map<String, Object> map = new HashMap<>();

	    User user = userRepository.findByEmail(authentication.getName());
	    if (user == null) {
	        map.put("status: ", false);
	        map.put("message: ", "User not found!");
	        throw new CustomException(map);
	    }

	    Optional<User> unFollowUserOptional = userRepository.findById(userId);
	    if (unFollowUserOptional.isEmpty()) {
	        map.put("status: ", false);
	        map.put("message: ", "User not found, invalid UserId!");
	        throw new CustomException(map);
	    }
	    User unFollowUser = unFollowUserOptional.get();

	    // unfollow
	    if (unFollowUser.getFollowers() != null && unFollowUser.getFollowers().contains(user)) {
	        unFollowUser.getFollowers().remove(user);
	    } else {
	        map.put("status: ", false);
	        map.put("message: ", "You are not following this user!");
	        throw new CustomException(map);
	    }

	    // decrease following (disassociate)
	    if (user.getFollowing() != null && user.getFollowing().contains(unFollowUser)) {
	        user.getFollowing().remove(unFollowUser);
	    }

	    User savedUser = userRepository.save(user); // save user and unFollowUser (disassociate)
	    if (savedUser == null) {
	        map.put("status: ", false);
	        map.put("message: ", "Unfollowing user unsuccessful! " + unFollowUser.getFullName());
	        throw new CustomException(map);
	    } else {
	        map.put("status: ", true);
	        map.put("message: ", "Unfollowed user (" + unFollowUser.getFullName() + ").");
	        return new DefaultResponse(map);
	    }
	}
	
	//------------------------------User-DTO-Methods--------------------------------------
	
	@Override
	public User userRequest_To_User(UserRequest userRequest) {
		User user = modelMapper.map(userRequest, User.class);
		return user;
	}


}
