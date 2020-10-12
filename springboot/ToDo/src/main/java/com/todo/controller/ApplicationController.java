package com.todo.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.todo.exception.TaskNotFoundException;
import com.todo.forms.UserForm;
import com.todo.forms.TaskForm;
import com.todo.models.AuthenticationRequest;
import com.todo.models.AuthenticationResponse;
import com.todo.schedule.TodoTaskScheduler;
import com.todo.service.UserSvcImpl;
import com.todo.service.MyUserDetailsService;
import com.todo.service.TaskManagerService;
import com.todo.util.JwtUtil;

@RestController
public class ApplicationController {
	
	@Autowired
	UserSvcImpl userSvc;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired 
	TaskManagerService taskMgrSvc;
	
	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Autowired
	TodoTaskScheduler todoSch;
	

	/*@Autowired
	private TaskRepository repo;
	
	@Autowired
	ElasticsearchRestTemplate elasticsearchTemplate;
	
	@Autowired
	RestHighLevelClient client;*/
	
	@RequestMapping(value="/test")
	public String test() {
		return "test";
	}
	
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		final String jwt = jwtTokenUtil.generateToken(userDetails);
		
		todoSch.runTaskScheduler(authenticationRequest.getUsername());
		
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
	
	@GetMapping(value = "/logout")
	public ResponseEntity<String> logOff() {
		SecurityContextHolder.clearContext();
		return new ResponseEntity<>("Log Out Successful", HttpStatus.OK);
	}
	
	@PostMapping(value="/registerUser")
	public void registerUser(@RequestBody UserForm regForm) {
		userSvc.addUser(regForm);
	}
	
	@GetMapping(value = "/getAllUsers")
	public ResponseEntity<Object> getAllUsers(@RequestParam(defaultValue = "1") String pageNo){
		Map<String, Object> users = userSvc.fetchAllUsers(pageNo);
		return ResponseEntity.ok().body(users);
	}
	
	@PostMapping(value = "/updateUser")
	public ResponseEntity<Object> updateUser(@RequestBody UserForm user){
		user = userSvc.updateUser(user);
		return ResponseEntity.ok().body(user);
	}
	
	@PostMapping(value = "/deleteUser")
	public ResponseEntity<Object> deleteUser(@RequestBody UserForm user){
		Map<String, Object> users = userSvc.removeUser(user);
		return ResponseEntity.ok().body(users);
	}
	
	@PostMapping(value="/createTask")
	public boolean createNewTask(@RequestBody TaskForm taskForm, Principal principal) {
		taskMgrSvc.createTask(taskForm, principal.getName());
		return true;
	}
	
	@GetMapping(value="/getTasks")
	public ResponseEntity<Object> getTasks(@RequestParam(defaultValue = "1") String pageNo, Principal principal) {
		Map<String, Object> tasksData = taskMgrSvc.retreiveAllTasksOfUser(pageNo, principal.getName());
		return ResponseEntity.ok().body(tasksData);
	}
	
	@PostMapping(value="/updateCompletion")
	public ResponseEntity<Object> updateCompletionStatus(@RequestBody TaskForm taskForm, Principal principal) {
		Map<String, Object>  tasksData =  taskMgrSvc.updateStatus(taskForm, principal.getName());
		return ResponseEntity.ok().body(tasksData);
	}
	
	
	@PostMapping(value="/getTask/{taskName}")
	public  ResponseEntity<Object> getTask(@PathVariable String taskName) throws TaskNotFoundException {
		Optional<TaskForm> task =  taskMgrSvc.getTask(taskName);
		return ResponseEntity.ok().body(task);
	}
	
	@PostMapping(value = "/deleteTask")
	public ResponseEntity<Object> deleteTask(@RequestBody TaskForm task, Principal principal) {
		Map<String, Object> taskData = taskMgrSvc.removeTask(task.getTaskName(), principal.getName());
		return ResponseEntity.ok().body(taskData);
	}
	
	/*@PostMapping(value="/getTaskElk/{taskName}")
	public List<TaskForm> getTaskElk(@PathVariable String taskName) throws TaskNotFoundException {
		return repo.findByTaskName(taskName);
	}
	
	@PostMapping(value="/createTaskElk")
	public TaskForm createNewTaskELK(@RequestBody TaskForm taskForm) {
		boolean isIndexPresent = elasticsearchTemplate.indexExists("taskslist");
		if(!isIndexPresent) {
			elasticsearchTemplate.createIndex("taskslist");
		}
		taskMgrSvc.createTask(taskForm);
		taskForm = repo.save(taskForm);
		return taskForm;
	}*/
}
