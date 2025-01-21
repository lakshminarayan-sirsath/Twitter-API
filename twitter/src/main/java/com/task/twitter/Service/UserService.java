package com.task.twitter.Service;

import com.task.twitter.RequestEntityDTO.UpdateUserProfileRequest;
import com.task.twitter.RequestEntityDTO.UserLoginRequest;
import com.task.twitter.RequestEntityDTO.UserRequest;
import com.task.twitter.ResponseEntityDTO.DefaultResponse;
import com.task.twitter.Table.User;

public interface UserService {
	
	DefaultResponse createNewUser(UserRequest userRequest);
	
	DefaultResponse userLogin(UserLoginRequest userLoginRequest);
	
	DefaultResponse updateUserProfile(UpdateUserProfileRequest updateUserProfileRequest); // DefaultResponse enditProfileOrAddMoreInformation(); // @@@@@@@@@@
	
	User findUserById(Long userId);
//	
	DefaultResponse findUserProfile();

	DefaultResponse followUser(Long userld);
	
	DefaultResponse unFollowUser(Long userld);
	
	
	
//	public List<User> searchUser(String query);
	
	
	//------------------------------User-DTO-Methods--------------------------------------
	
	User userRequest_To_User(UserRequest userRequest);
}
