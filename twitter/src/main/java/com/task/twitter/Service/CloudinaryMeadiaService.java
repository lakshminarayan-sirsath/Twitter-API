package com.task.twitter.Service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryMeadiaService {
	
	public Map upload(MultipartFile file);
}
