package com.todo.schedule;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import com.todo.dao.TaskManagerDao;
import com.todo.util.EmailHelper;

@Component
public class TodoTaskScheduler {
	private static final Logger logger = LoggerFactory.getLogger(TodoTaskScheduler.class);
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");


	@Autowired
	TaskManagerDao taskDao;

	@Autowired
	WebSocketConnector dispatcher;
	
	public void runTaskScheduler(String userId) {
		EmailHelper email = new EmailHelper();
		String everyMinExp = "0 * * ? * *";
		CronTrigger cronTriggerExp = new CronTrigger(everyMinExp);
		SchedulerConfig.configureScheduler().schedule(new ExecuteTask(userId, email, dispatcher,taskDao), cronTriggerExp);
		logger.info("Task: End Time - {}", formatter.format(LocalDateTime.now()));
	}

}
