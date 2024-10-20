package com.project.staffsync.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.project.staffsync.dto.AuthRequest;
import com.project.staffsync.dto.User;
import com.project.staffsync.dto.Role;
import com.project.staffsync.service.StaffService;
import com.project.staffsync.service.JwtService;
import com.project.staffsync.service.StudentService;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    
    @Autowired
    private StudentService studentService;
    
    @Autowired
    private StaffService staffService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/addStudent")
    public ResponseEntity<?> addPatient(@RequestBody User user) {
    	try {
    		return ResponseEntity.ok(studentService.addStudent(user));
    	} catch(Exception e) {
    		return ResponseEntity.badRequest().body(e.getMessage());
    	}
    }
    
    @PostMapping("/addStaff")
    public ResponseEntity<?> addStaff(@RequestBody User user) {
    	try {
    		return ResponseEntity.ok(staffService.addStaff(user));
    	} catch(Exception e) {
    		return ResponseEntity.badRequest().body(e.getMessage());
    	}
    }

    @PostMapping("/generateToken")
    public ResponseEntity<?> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        try {
        	Authentication authentication = authenticationManager.authenticate(
        			new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
            if (authentication.isAuthenticated()) {
            	String token = jwtService.generateToken(authRequest.getUsername());
            	return ResponseEntity.ok(token);
            } else {
            	throw new UsernameNotFoundException("Invalid user request!");
            }
        }catch(Exception e) {
        	e.printStackTrace();
        	return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}