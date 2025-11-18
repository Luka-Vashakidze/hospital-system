package com.solvd.hospital.service;

import com.solvd.hospital.domain.Appointment;
import com.solvd.hospital.domain.AppointmentDetail;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentService {
    Appointment save(Appointment appointment);

    Optional<Appointment> get(Long id);

    List<Appointment> getAll();

    List<Appointment> findByDoctor(Long doctorId);

    List<Appointment> findByPatient(Long patientId);

    List<AppointmentDetail> upcomingDetails(LocalDateTime fromDateTime);

    Appointment update(Appointment appointment);

    boolean remove(Long id);
}
