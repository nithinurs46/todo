package com.todo.schedule;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

public class SchedulerConfig {

	private static int POOL_SIZE = 5;

	public static ThreadPoolTaskScheduler configureScheduler() {

		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setPoolSize(POOL_SIZE);
		scheduler.setThreadNamePrefix("todo-app-task-pool-");
		scheduler.initialize();
		return scheduler;

	}

}
