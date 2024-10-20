package com.project.staffsync.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.staffsync.dto.Appointment;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByStudentId(Long studentId);
    List<Appointment> findByStaffId(Long staffId);
}
