package com.task.twitter.GlobalException;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException {
	private final Map<String, Object> additionalDetails;
}
