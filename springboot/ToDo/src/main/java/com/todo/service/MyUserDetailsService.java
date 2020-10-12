package com.todo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.todo.dao.UserRepository;
import com.todo.forms.UserForm;
import com.todo.models.MyUserDetails;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository repo;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Optional<UserForm> user = repo.findByusername(userName);
		user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName));
		return user.map(MyUserDetails::new).get();
	}

}
