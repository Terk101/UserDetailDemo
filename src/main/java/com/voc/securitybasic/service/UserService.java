package com.voc.securitybasic.service;

import com.voc.securitybasic.domain.User;
import com.voc.securitybasic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements UserDetailsService, IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        try {
            return userOptional.map(p -> {
                Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
                p.getRoles().stream()
                        .forEach(
                                r -> grantedAuthorities.add(new SimpleGrantedAuthority(r)));

                return new org.springframework.security.core.userdetails.User(p.getUserName()
                        , p.getPassword(),
                        grantedAuthorities);
            }).orElseThrow(() -> new UsernameNotFoundException("User not found " + email));
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Long saveUser(User user) {
        String password = user.getPassword();
        String encodePassword = bCryptPasswordEncoder.encode(password);
        user.setPassword(encodePassword);

        User createdUser = userRepository.save(user);
        return createdUser.getId();
    }
}
