package com.todo.exception;

public class TaskNotFoundException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TaskNotFoundException(String taskName) {
		super("Task "+taskName+ " not found");
	}

}
