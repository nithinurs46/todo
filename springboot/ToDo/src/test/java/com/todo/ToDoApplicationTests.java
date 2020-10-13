package com.todo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.dao.TaskManagerDao;
import com.todo.dao.UserRepository;
import com.todo.forms.TaskForm;
import com.todo.forms.UserForm;
import com.todo.models.MyUserDetails;
import com.todo.models.Response;
import com.todo.service.MyUserDetailsService;
import com.todo.service.TaskManagerService;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class ToDoApplicationTests {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	ObjectMapper om = new ObjectMapper();
	
	@Autowired
	MyUserDetailsService myUserDetailsService;

	@BeforeAll
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	@WithUserDetails(value="AB")
	public void createNewTaskTest() throws Exception { // test the controller
		TaskForm task = new TaskForm();
		task.setTaskName("testing113 app18");
		task.setCompleted("true");
		task.setDescription("description");
		task.setTaskDate(new Date());

		String jsonRequest = om.writeValueAsString(task);

		
		MvcResult result = mockMvc
				.perform(post("/createTask").content(jsonRequest).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();
		String resultContent = result.getResponse().getContentAsString();
		Response response = om.readValue(resultContent, Response.class);
		Assertions.assertTrue(response.isStatus() == Boolean.TRUE);

	}
	
	//test specific service --
	
	
	@Autowired
	TaskManagerService taskMgrSvc;
	
	@MockBean
	TaskManagerDao dao;
	
	@Test
	void testGetAllTasks() {
		//when(dao.getAllTasks().size()).thenReturn(4);
		//assertEquals(4, dao.getAllTasks().size());
		List<TaskForm> mockTaskList = new ArrayList<TaskForm>();
		mockTaskList = Stream.of(new TaskForm("name1",new Date(), "desc1", "completed1"),
				new TaskForm("name2",new Date(), "desc2", "completed2")).collect(Collectors.toList());
		Mockito.when(dao.getAllTasks())
	      .thenReturn(mockTaskList);
		
		List<TaskForm> taskList = dao.getAllTasks();
		assertThat(taskList.get(0).getTaskName())
	      .isEqualTo("name1");
	}
	
}
