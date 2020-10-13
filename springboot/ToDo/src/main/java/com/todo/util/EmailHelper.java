package com.todo.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.todo.forms.TaskForm;

public class EmailHelper {

	public String emailSender = "tsmonitor@jadolisystems.com";
	public String emailRecipient = "nithinurs.venugopalrajurs@arisglobal.com";
	public String officePassword = "Password1";
	public String host = "192.168.150.160";
	public String port = "25";


	public void sendEmail(TaskForm task) {

		String notificationSubject = task.getTaskName() + " Notification!! ";
		StringBuilder msgBodyBuilder = new StringBuilder();

		msgBodyBuilder.append("<table border=\"1\" cellpadding = \"5\" cellspacing = \"0\">");
		msgBodyBuilder.append("<tr><th align='left'><B>Task Name </B></th><th align='left'><B>Task Due Date </B></th>");
		msgBodyBuilder.append("<th align='left'><B>Task Description</B></th>");
		msgBodyBuilder.append("<th align='left'><B>Task Status </B></th><tr>");
		msgBodyBuilder.append("<tr><td>" + task.getTaskName() + "</td><td>" + task.getTaskDate() + "</td>");
		msgBodyBuilder.append("<td>" + task.getDescription() + "</td><td>" + task.getCompleted() + "</td>");
		msgBodyBuilder.append("</tr></table>\n");

		String msgBody = msgBodyBuilder.toString();
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.auth", "true");
		

		Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(emailSender, officePassword);
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(emailSender));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailRecipient));
			message.setSubject(notificationSubject);
			message.setContent(msgBody, "text/html");
			Transport.send(message);

		} catch (MessagingException e) {
			System.out.println("Failed to send email..");
			e.printStackTrace();
		}

	}
}
