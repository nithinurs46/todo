package com.todo.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class AuthenticationFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;

		/*System.out.println("SecurityContextHolder.getContext().getAuthentication().getName():::: "
				+ SecurityContextHolder.getContext().getAuthentication().getName());*/
		String pageURI = request.getRequestURI();
		System.out.println("pageURI:- " + pageURI);

		chain.doFilter(req, res);

	}

}
