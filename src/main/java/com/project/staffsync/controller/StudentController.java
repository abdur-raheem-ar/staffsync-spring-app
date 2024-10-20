package com.project.staffsync.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.staffsync.dto.User;
import com.project.staffsync.service.StudentService;

@RestController
@RequestMapping("api/student")
public class StudentController {
	
	@Autowired
    private StudentService studentService;
	
	@GetMapping("/profile")
	@PreAuthorize("hasAuthority('ROLE_STUDENT')")
	public ResponseEntity<?> studentProfile() {
		try {
	  	   Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	       String username;
	       if (principal instanceof UserDetails) {
	          username = ((UserDetails) principal).getUsername();
	       } else {
	          username = principal.toString();
	       }
	        
	        // Fetch patient details from the database using the UserService
	        User student = studentService.getStudentByEmail(username);
	  		System.out.println("profile called..");
	  		return ResponseEntity.ok(student);
	  	}catch(Exception e) {
	  		e.printStackTrace();
	  		return ResponseEntity.badRequest().body(e.getMessage());
	  	}
	}
}
