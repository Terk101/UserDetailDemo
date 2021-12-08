package com.voc.securitybasic.config;

import com.voc.securitybasic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter  {

    @Resource
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private static String REALM="MY_TEST_REALM";

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);

        return authenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //TODO Override to  implement Authentication logic
        //TODO configure authentication manager
        auth.authenticationProvider(authProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //TODO Override to implement Authorization logic
        //TODO configure web security
        http.httpBasic()
                .and().httpBasic().realmName(REALM).authenticationEntryPoint(getBasicAuthEntryPoint()).and()
                .authorizeRequests().antMatchers("/h2-console/**", "/", "/home", "/register", "/saveUser").permitAll()
                .antMatchers("/welcome").authenticated()
                .antMatchers("/admin").hasAnyAuthority("ADMIN")
                .antMatchers("/emp").hasAnyAuthority("EMPLOYEE")
                .antMatchers("/mgr").hasAnyAuthority("MANAGER")
                .antMatchers("/common").hasAnyRole("EMPLOYEE", "ADMIN", "MANAGER")
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//We don't need sessions to be created.

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

    @Bean
    public CustomBasicAuthenticationEntryPoint getBasicAuthEntryPoint(){
        return new CustomBasicAuthenticationEntryPoint();
    }

}
