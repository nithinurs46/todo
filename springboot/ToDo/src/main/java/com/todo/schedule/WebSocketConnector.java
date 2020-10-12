package com.todo.schedule;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.forms.Notification;
import com.todo.forms.TaskForm;

@Component
public class WebSocketConnector {
	private static final Logger logger = LoggerFactory.getLogger(WebSocketConnector.class);

	public WebSocketConnector(SimpMessagingTemplate template) {
		this.template = template;
	}

	private final SimpMessagingTemplate template;

	private Set<String> listeners = new HashSet<>();

	public void add(String sessionId) {
		listeners.add(sessionId);
	}

	public void remove(String sessionId) {
		listeners.remove(sessionId);
	}

	public void dispatch(TaskForm task) {
		for (String listener : listeners) {
			logger.info("Sending notification to " + listener);

			SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
			headerAccessor.setSessionId(listener);
			headerAccessor.setLeaveMutable(true);
			ObjectMapper obj = new ObjectMapper();
			String jsonStr = "";
			try {
				String msg = task.getTaskName() + "due on "+ task.getTaskDate() + " at "+task.getTime();
				jsonStr = obj.writeValueAsString(new Notification("Reminder", msg));
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			template.convertAndSendToUser(listener, "/notification/item", jsonStr,
					headerAccessor.getMessageHeaders());
		}
	}

	@EventListener
	public void sessionDisconnectionHandler(SessionDisconnectEvent event) {
		String sessionId = event.getSessionId();
		logger.info("Disconnecting " + sessionId + "!");
		remove(sessionId);
	}
}
