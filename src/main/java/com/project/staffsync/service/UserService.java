package com.project.staffsync.service;

import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.project.staffsync.dto.Role;
import com.project.staffsync.dto.User;
import com.project.staffsync.repository.UserRepository;


@Service
public class UserService implements UserDetailsService {
	
	private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	System.out.println("loadUserByUsername called...");
    	User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalStateException("User not found with email: " + username));
        System.out.println(user);
        System.out.println("After query...");
        return user;
    }

//    private Collection<? extends GrantedAuthority> getAuthorities(Role role) {
//        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
//    }    
    
}