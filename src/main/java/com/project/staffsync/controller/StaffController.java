package com.project.staffsync.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.staffsync.dto.User;
import com.project.staffsync.service.StaffService;

@RestController
@RequestMapping("api/staff")
public class StaffController {
	
	@Autowired
    private StaffService staffService;
	
	@GetMapping("/profile")
	@PreAuthorize("hasAuthority('ROLE_STAFF')")
	public ResponseEntity<?> staffProfile() {
		try {
	  	   Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	       String username;
	       if (principal instanceof UserDetails) {
	          username = ((UserDetails) principal).getUsername();
	       } else {
	          username = principal.toString();
	       }
	        
	        // Fetch doctor details from the database using the UserService
	        User staff = staffService.getStaffByEmail(username);
	  		System.out.println("Doctor profile called..");
	  		return ResponseEntity.ok(staff);
	  	}catch(Exception e) {
	  		e.printStackTrace();
	  		return ResponseEntity.badRequest().body(e.getMessage());
	  	}
	}
	
	@GetMapping("/department/{department}")
	@PreAuthorize("hasAuthority('ROLE_STUDENT')")
	public ResponseEntity<?> getStaffsByDepartment(@PathVariable String department) {
		System.out.println(department);
		return ResponseEntity.ok(staffService.getStaffsByDepartment(department));
	}
}