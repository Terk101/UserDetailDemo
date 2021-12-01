package com.voc.securitybasic;

import com.voc.securitybasic.domain.User;
import com.voc.securitybasic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;

@SpringBootApplication
public class SecurityBasicApplication implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(SecurityBasicApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        User user1 = new User();
        user1.setUserName("Dummy1");
        user1.setEmail("Dummy1@mail.com");
        user1.setPassword(bCryptPasswordEncoder.encode("1234"));
        user1.setRoles(Arrays.asList("ADMIN", "EMPLOYEE"));
        userRepository.save(user1);
    }
}
