package com.task.twitter.RequestEntityDTO;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserProfileRequest {
	
	private String fullName = null;
	
	private String location = null;
	
	private String website = null;
	
	private String birthDate = null;
	
	@Size(min = 10, max = 10, message = "Mobile number must be 10 digits.")
	private String mobile = null;
	
	private String image = null;
	
	private String backgroundImage = null;

	private String bio = null;

//	private boolean reqUser;
//
//	private boolean loginWithGoogle;
	
//	private Varification verification;
	
}
