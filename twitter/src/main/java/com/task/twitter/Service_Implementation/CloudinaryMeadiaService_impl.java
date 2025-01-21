package com.task.twitter.Service_Implementation;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.task.twitter.Service.CloudinaryMeadiaService;

@Service
public class CloudinaryMeadiaService_impl implements CloudinaryMeadiaService {

	// Dependency injection
	@Autowired
	private Cloudinary cloudinary;
	
	@Override
	public Map upload(MultipartFile file) {
//		Map<String, Object> responseMap = new HashMap<>();
		
		try {
			Map data = this.cloudinary.uploader().upload(file.getBytes(), Map.of());
			return data;
//			responseMap.put("status: ", true);
//			responseMap.put("message: ", "file uploaded successfully.");
//			responseMap.put("file-data: ", data);
//			return new DefaultResponse(responseMap);
			
		} catch (Exception e) {
			throw new RuntimeException("File uploading fail !!");
		}
	}

}
