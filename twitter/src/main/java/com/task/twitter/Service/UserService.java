package com.task.twitter.Service;

import com.task.twitter.RequestEntityDTO.UserLoginRequest;
import com.task.twitter.RequestEntityDTO.UserRequest;
import com.task.twitter.ResponseEntityDTO.DefaultResponse;
import com.task.twitter.Table.User;

public interface UserService {
	
	DefaultResponse createNewUser(UserRequest userRequest);
	
	DefaultResponse userLogin(UserLoginRequest userLoginRequest);
	
	//------------------------------User-DTO-Methods--------------------------------------
	
	User userRequest_To_User(UserRequest userRequest);
}
