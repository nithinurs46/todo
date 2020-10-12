package com.todo.schedule;

import java.util.Date;
import java.util.List;

import com.todo.dao.TaskManagerDao;
import com.todo.forms.TaskForm;
import com.todo.util.EmailHelper;

public class ExecuteTask implements Runnable {

	private EmailHelper eMail;
	private WebSocketConnector dispatcher;
	private TaskManagerDao taskDao;
	private String userId;

	public ExecuteTask() {

	}

	public ExecuteTask(String userId, EmailHelper eMail, WebSocketConnector dispatcher, TaskManagerDao taskDao) {
		this.eMail = eMail;
		this.dispatcher = dispatcher;
		this.taskDao = taskDao;
		this.userId = userId;
	}

	@Override
	public void run() {
		System.out.println("In Run Method");
		runJob();

	}

	private void runJob() {
		System.out.println("Running jobs");
		List<TaskForm> tasks = taskDao.getCompletedTasksOfUser(userId);
		if (tasks != null && tasks.size() > 0) {
			for (TaskForm task : tasks) {
				Date taskDate = task.getTaskDate();
				Date sysDate = taskDao.getSysDate();
				if (taskDate.equals(sysDate)) {
					notifyUser(task);
				} else if (taskDate.after(sysDate)) {
					notifyUser(task);
				} else if (taskDate.before(sysDate)) {
					notifyUser(task);
				} else {
					notifyUser(task);
				}
			}
		}
	}

	private void notifyUser(TaskForm task) {
		System.out.println("Sending notification");
		// eMail.sendEmail(task);
		dispatcher.dispatch(task);
	}

}
