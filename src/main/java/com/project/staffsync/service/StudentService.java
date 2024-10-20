package com.project.staffsync.service;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
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
public class StudentService {
	
	private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public StudentService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    public User getStudentByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> 
            new IllegalStateException("User not found")
        );
        
        if (user.getRole().equals(Role.STUDENT)) {
            return user;
        } else {
            throw new IllegalStateException("User is not a student");
        }
    }
        
    public User addStudent(User user) throws IllegalStateException {
        // 1. Check if the email already exists
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalStateException("Email is already registered.");
        }

        // 2. Validate email format
        if (!isValidEmail(user.getEmail())) {
            throw new IllegalStateException("Invalid email format.");
        }

        // 3. Validate password strength
        if (!isValidPassword(user.getPassword())) {
            throw new IllegalStateException("Password must be at least 8 characters, contain one number, one letter, and one special character.");
        }

        // 4. Encrypt the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 5. Save the user to the database
        return userRepository.save(user);
    }

    // Email validation using regex
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        return StringUtils.hasText(email) && pattern.matcher(email).matches();
    }

    // Password strength validation
    private boolean isValidPassword(String password) {
        if (password.length() < 8) {
            return false;
        }
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=]).{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        return pattern.matcher(password).matches();
    }
}