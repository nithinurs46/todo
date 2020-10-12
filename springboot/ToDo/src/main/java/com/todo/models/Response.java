package com.todo.models;


public class Response {
	private String message;
	private boolean status;
	
	public Response() {
	}
	public Response(boolean status) {
		this.status = status;
	}
	public Response(String message, boolean status) {
		this.message = message;
		this.status = status;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
}
