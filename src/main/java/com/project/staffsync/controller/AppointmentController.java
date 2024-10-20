package com.project.staffsync.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.staffsync.dto.Appointment;
import com.project.staffsync.service.AppointmentService;

import java.util.List;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    public ResponseEntity<?> createAppointment(@RequestBody Appointment appointment) {
        try {
        	System.out.println(appointment);
            Appointment createdAppointment = appointmentService.createAppointment(appointment);
            return new ResponseEntity<>(createdAppointment, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    public ResponseEntity<?> getAppointmentsForPatient(@PathVariable Long studentId) {
        try {
            List<Appointment> appointments = appointmentService.getAppointmentsForStudent(studentId);
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/staff/{staffId}")
    @PreAuthorize("hasAuthority('ROLE_STAFF')")
    public ResponseEntity<?> getAppointmentsForDoctor(@PathVariable Long staffId) {
        try {
            List<Appointment> appointments = appointmentService.getAppointmentsForStaff(staffId);
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("/approve/{appointmentId}")
    @PreAuthorize("hasAnyAuthority('ROLE_STAFF')")
    public ResponseEntity<?> approveAppointment(@PathVariable Long appointmentId) {
        try {
            Appointment canceledAppointment = appointmentService.approveAppointment(appointmentId);
            return ResponseEntity.ok(canceledAppointment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/cancel/{appointmentId}")
    @PreAuthorize("hasAnyAuthority('ROLE_STAFF')")
    public ResponseEntity<?> cancelAppointment(@PathVariable Long appointmentId) {
        try {
            Appointment canceledAppointment = appointmentService.cancelAppointment(appointmentId);
            return ResponseEntity.ok(canceledAppointment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
