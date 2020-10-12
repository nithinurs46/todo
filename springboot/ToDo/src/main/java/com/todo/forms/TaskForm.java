package com.todo.forms;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;

//@Document(indexName = "taskslist", type = "tasks" )
@Entity(name = "TODO_TASKS")
@Table(name = "TODO_TASKS")
@DynamicUpdate
public class TaskForm {

	@Id
	@Column(name = "TASK_NAME")
	private String taskName;

	@Column(name = "TASK_DATE")
	@Temporal(TemporalType.DATE)
	private Date taskDate;

	private String description;

	private String completed;

	private String time;

	@Transient
	private int hh;

	@Transient
	private int mm;

	@Column(name = "USER_ID")
	private String userId;

	public TaskForm() {
	}

	public TaskForm(String taskName, Date taskDate, String description, String completed) {
		this.taskName = taskName;
		this.taskDate = taskDate;
		this.description = description;
		this.completed = completed;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Date getTaskDate() {
		return taskDate;
	}

	public void setTaskDate(Date taskDate) {
		this.taskDate = taskDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCompleted() {
		return completed;
	}

	public void setCompleted(String completed) {
		this.completed = completed;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int gethh() {
		return hh;
	}

	public void sethh(int hh) {
		this.hh = hh;
	}

	public int getmm() {
		return mm;
	}

	public void setmm(int mm) {
		this.mm = mm;
	}

}
