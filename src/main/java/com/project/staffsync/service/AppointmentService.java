package com.project.staffsync.service;

import org.springframework.stereotype.Service;

import com.project.staffsync.dto.Appointment;
import com.project.staffsync.repository.AppointmentRepository;

import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public Appointment createAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }
    
    public Appointment approveAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalStateException("Appointment not found with ID: " + appointmentId));

        appointment.setStatus("SCHEDULED");
        return appointmentRepository.save(appointment);
    }
    
    public Appointment cancelAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalStateException("Appointment not found with ID: " + appointmentId));

        appointment.setStatus("CANCELLED");
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAppointmentsForStudent(Long studentId) {
        return appointmentRepository.findByStudentId(studentId);
    }

    public List<Appointment> getAppointmentsForStaff(Long staffId) {
        return appointmentRepository.findByStaffId(staffId);
    }
}
