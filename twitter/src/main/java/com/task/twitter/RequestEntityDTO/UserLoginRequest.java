package com.task.twitter.RequestEntityDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginRequest {
	
	@Email
	private String email; // User-name
	
	@NotBlank(message = "Password can not be blank")
	private String password;
}
