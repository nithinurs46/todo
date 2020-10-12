package com.todo;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
//@EnableJpaRepositories(basePackageClasses=UserRepository.class)
@EnableScheduling
public class ToDoApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(ToDoApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/*").allowedHeaders("*").allowedOrigins("*").allowedMethods("*")
						.allowCredentials(true);
				;
			}
		};
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// todoSch.getTasksToSchedule();
	}
}
