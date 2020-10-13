package com.todo;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.security.test.context.support.WithSecurityContext;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Repeatable(WithUserDetailsContainer.class)
@WithSecurityContext(factory = WithUserDetailsSecurityContextFactory.class)
public @interface WithUserDetails {

    
    String value() default "user";

    String userDetailsServiceBeanName() default "";
}
