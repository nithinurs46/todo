package com.todo.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionAdvisor extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleException(Exception e) {
		Map<String, Object> body = new LinkedHashMap<String, Object>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", e.getMessage());
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(TaskNotFoundException.class)
	public ResponseEntity<Object> handleException(TaskNotFoundException e, WebRequest req) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", "Task not found");
		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}

}
