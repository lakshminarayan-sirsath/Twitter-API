package com.task.twitter.Configuration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;

@Configuration
public class CloudinaryCinfig {

	@Bean
	public Cloudinary getCloudinary() {
	    Map config = new HashMap();
	    config.put("cloud_name", "diwip4php");
	    config.put("api_key", "778248753239559");
	    config.put("api_secret", "OkcXpz8fWfDku3UAQhf46ZeH2Xk");
	    config.put("secure", true);

	    return new Cloudinary(config);
	}

}
