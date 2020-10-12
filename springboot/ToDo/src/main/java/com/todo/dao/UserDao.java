package com.todo.dao;

import java.util.Map;

import com.todo.forms.UserForm;

public interface UserDao {

	public UserForm getUser(String userName);

	public void addUser(UserForm regForm);

	public UserForm updateUser(UserForm form);

	public void deleteUser(String username);

	public Map<String, Object> getAllUsers(int pageNo);
	
}
