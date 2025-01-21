package com.task.twitter.RestController;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.task.twitter.Service_Implementation.CloudinaryMeadiaService_impl;

@RestController
@RequestMapping("/cloudinary/upload_file")
public class CloudinaryController {

	// Dependency Injection
	@Autowired
	CloudinaryMeadiaService_impl cloudinaryMeadiaService_impl;
	
	// upload file
	@PostMapping
	public ResponseEntity<Map> upload(@PathVariable("image") MultipartFile file) {
	    Map data = cloudinaryMeadiaService_impl.upload(file);
	    return new ResponseEntity<>(data, HttpStatus.OK);
	}	
	
}
