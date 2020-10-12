package com.todo.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todo.forms.UserForm;

public interface UserRepository extends JpaRepository<UserForm, Integer> {
	Optional<UserForm> findByusername(String userName);
}
