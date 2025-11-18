package com.solvd.hospital.service.impl;

import com.solvd.hospital.domain.Appointment;
import com.solvd.hospital.domain.AppointmentDetail;
import com.solvd.hospital.persistence.AppointmentRepository;
import com.solvd.hospital.persistence.impl.AppointmentRepositoryImpl;
import com.solvd.hospital.service.AppointmentService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;

    public AppointmentServiceImpl() {
        this.appointmentRepository = new AppointmentRepositoryImpl();
    }

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public Appointment save(Appointment appointment) {
        return appointmentRepository.create(appointment);
    }

    @Override
    public Optional<Appointment> get(Long id) {
        return appointmentRepository.findById(id);
    }

    @Override
    public List<Appointment> getAll() {
        return appointmentRepository.findAll();
    }

    @Override
    public List<Appointment> findByDoctor(Long doctorId) {
        return appointmentRepository.findByDoctor(doctorId);
    }

    @Override
    public List<Appointment> findByPatient(Long patientId) {
        return appointmentRepository.findByPatient(patientId);
    }

    @Override
    public List<AppointmentDetail> upcomingDetails(LocalDateTime fromDateTime) {
        return appointmentRepository.findUpcomingDetails(fromDateTime);
    }

    @Override
    public Appointment update(Appointment appointment) {
        return appointmentRepository.update(appointment);
    }

    @Override
    public boolean remove(Long id) {
        return appointmentRepository.deleteById(id);
    }
}
