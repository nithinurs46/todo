package com.todo.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.dao.TaskManagerDao;
import com.todo.exception.TaskNotFoundException;
import com.todo.forms.TaskForm;

@Service
public class TaskManagerService {

	@Autowired
	TaskManagerDao dao;
	
	public boolean createTask(TaskForm taskForm, String userId) {
		taskForm.setUserId(userId);
		taskForm.setTime(taskForm.gethh() + ":" + taskForm.getmm());
		dao.addNewTask(taskForm);
		return true;
	}
	
	public List<TaskForm> getTasksList() {
		return dao.getAllTasks();
	}
	
	public Map<String, Object>  updateStatus(TaskForm taskForm, String userName) {
		return dao.updateStatus(taskForm, userName);
	}
	
	public Optional<TaskForm> getTask(String taskName) throws TaskNotFoundException {
		Optional<TaskForm> task = dao.getTask(taskName);
		task.orElseThrow(() -> new TaskNotFoundException(taskName));
		return task;
	}
	
	public Map<String, Object> retreiveAllTasksOfUser(String pageNo, String userId){
		Map<String, Object> tasks = new HashMap<String, Object>();
		if(pageNo!=null && pageNo.length()>0) {
			int page = Integer.valueOf(pageNo);
			tasks =  dao.getAllTasksOfUser(page, userId);
		}
		return tasks;
	}
	
	public Map<String, Object> removeTask(String taskName, String userId) {
		dao.deleteTask(taskName);
		return dao.getAllTasksOfUser(1, userId);
	}
}
