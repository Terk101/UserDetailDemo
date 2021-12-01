package com.voc.securitybasic.config;

import com.voc.securitybasic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //TODO Override to  implement Authentication logic
        //TODO configure authentication manager
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //TODO Override to implement Authorization logic
        //TODO configure web security
        http.authorizeHttpRequests()
                .antMatchers("/").permitAll().and()
                .authorizeRequests().antMatchers("/h2-console/**").permitAll()
                .antMatchers("/home", "/register", "/saveUser").permitAll()
                .antMatchers("/welcome").authenticated()
                .antMatchers("/admin").hasAnyAuthority("ADMIN")
                .antMatchers("/emp").hasAnyAuthority("EMPLOYEE")
                .antMatchers("/mgr").hasAnyAuthority("MANAGER")
                .antMatchers("/common").hasAnyAuthority("EMPLOYEE", "MANAGER", "ADMIN")
                .anyRequest().authenticated();

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }
}
