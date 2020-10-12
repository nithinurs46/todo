package com.todo.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Aspects {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	// execution -- matches all the methods in the class

	@Pointcut("execution(* com.todo.controller.ApplicationController.*(..))")
	public void controllerClassMethods() {
	}

	@Before("controllerClassMethods()")
	public void beforeExecution(JoinPoint joinPoint) {
		logger.info("invoking method: " + joinPoint);
	}

	// execution for a particular method --

	@Pointcut("execution(* com.todo.daoImpl.TaskManagerDaoImpl.getAllTasks())")
	public void forGetAllTasks() {
	}

	@Before("forGetAllTasks()")
	public void afterGetAllTasks(JoinPoint joinPoint) {
		logger.info("invoking method *****: " + joinPoint);
	}

	@Pointcut("within(com.todo.daoImpl..*)")
	public void withinDAOImpl() {
	}

	@After("withinDAOImpl()")
	public void afterAllDao(JoinPoint joinPoint) {
		logger.info("invoking method afterAllDao *****: " + joinPoint.getArgs());
	}
	
	
	// below joinPoint.proceed executes before and after the method execution
	/*@Around("execution(* com.todo.daoImpl.TaskManagerDaoImpl.getAllTasks())")
	public void userAdvice(ProceedingJoinPoint joinPoint) throws Throwable{
		System.out.println("find my advice before the method...");
		System.out.println(joinPoint.getKind());
		System.out.println(joinPoint.getTarget());
		Object[] intA = joinPoint.getArgs();
		for(int i=0;i<intA.length;i++){
			System.out.println(intA[i]);
		}
		joinPoint.proceed();
		System.out.println(joinPoint.getSignature().getName());
		System.out.println("find my advice after the method...");
	}*/
	
}
