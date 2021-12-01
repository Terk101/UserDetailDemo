package com.voc.securitybasic.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //TODO Override to  implement Authentication logic
        //TODO configure authentication manager
        auth
                .inMemoryAuthentication()
                .withUser("devs")
                .password("{noop} =devs")
                .authorities("ADMIN");

        auth.inMemoryAuthentication()
                .withUser("ns")
                .password("{noop}ns").
                authorities("EMPLOYEE");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //TODO Override to implement Authorization logic
        //TODO configure web security
        http.authorizeHttpRequests()
                .antMatchers("/home").permitAll()
                .antMatchers("/welcome").authenticated()
                .antMatchers("/admin").hasAnyAuthority("ADMIN")
                .antMatchers("/emp").hasAnyAuthority("EMPLOYEE")
                .antMatchers("/mgr").hasAnyAuthority("MANAGER")
                .antMatchers("/common").hasAnyAuthority("EMPLOYEE", "MANAGER")
                .anyRequest().authenticated();
    }
}
