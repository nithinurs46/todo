package com.todo;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class WithUserDetailsSecurityContextFactory implements WithSecurityContextFactory<WithUserDetails> {

	private BeanFactory beans;

	@Autowired
	public WithUserDetailsSecurityContextFactory(BeanFactory beans) {
		this.beans = beans;
	}

	public SecurityContext createSecurityContext(WithUserDetails withUser) {
		String beanName = withUser.userDetailsServiceBeanName();
		UserDetailsService userDetailsService = StringUtils.hasLength(beanName)
				? this.beans.getBean(beanName, UserDetailsService.class)
				: this.beans.getBean(UserDetailsService.class);
		String username = withUser.value();
		Assert.hasLength(username, "value() must be non empty String");
		UserDetails principal = userDetailsService.loadUserByUsername(username);
		Authentication authentication = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(),
				principal.getAuthorities());
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(authentication);
		return context;
	}
}