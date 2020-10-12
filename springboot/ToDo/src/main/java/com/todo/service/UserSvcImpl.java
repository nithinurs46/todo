package com.todo.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.todo.dao.UserDao;
import com.todo.forms.UserForm;

@Service
public class UserSvcImpl {

	@Autowired
	UserDao userDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public void addUser(UserForm form) {
		form.setPassword(passwordEncoder.encode(form.getPassword()));
		form.setCreatedDate(new Date());
		form.setActive(true);
		form.setRole("ADMIN");
		userDao.addUser(form);
	}
	
	public Map<String, Object> fetchAllUsers(String pageNo){
		return userDao.getAllUsers(Integer.parseInt(pageNo));
	}
	
	public UserForm updateUser(UserForm form) {
		return userDao.updateUser(form);
	}
	
	public Map<String, Object> removeUser(UserForm form) {
		userDao.deleteUser(form.getUsername());
		return userDao.getAllUsers(1);
	}
}
