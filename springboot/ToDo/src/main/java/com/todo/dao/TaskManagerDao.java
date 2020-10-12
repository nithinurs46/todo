package com.todo.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.todo.forms.TaskForm;


public interface TaskManagerDao {

	public Optional<TaskForm> getTask(String taskName);

	public boolean addNewTask(TaskForm taskForm);

	public boolean updateTask(TaskForm taskForm);

	public boolean deleteTask(String taskName);

	public List<TaskForm> getAllTasks();
	
	public Map<String, Object> updateStatus(TaskForm taskForm, String userName);

	Map<String, Object> getAllTasksOfUser(int pageNo, String userId);

	Date getSysDate();

	List<TaskForm> getCompletedTasksOfUser(String userId);
	
}
