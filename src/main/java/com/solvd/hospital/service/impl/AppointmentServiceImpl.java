package com.solvd.hospital.service.impl;

import com.solvd.hospital.domain.Appointment;
import com.solvd.hospital.domain.AppointmentDetail;
import com.solvd.hospital.persistence.AppointmentRepository;
import com.solvd.hospital.persistence.mybatis.impl.AppointmentRepositoryMyBatisImpl;
import com.solvd.hospital.patterns.listener.AppointmentEventPublisher;
import com.solvd.hospital.patterns.strategy.BillingStrategy;
import com.solvd.hospital.patterns.strategy.StandardBillingStrategy;
import com.solvd.hospital.service.AppointmentService;

import java.time.LocalDateTime;
import java.util.List;

public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final BillingStrategy billingStrategy;
    private final AppointmentEventPublisher appointmentEventPublisher;

    public AppointmentServiceImpl() {
        this(new AppointmentRepositoryMyBatisImpl(), new StandardBillingStrategy(), new AppointmentEventPublisher());
    }

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
        this(appointmentRepository, new StandardBillingStrategy(), new AppointmentEventPublisher());
    }

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository,
                                  BillingStrategy billingStrategy,
                                  AppointmentEventPublisher appointmentEventPublisher) {
        this.appointmentRepository = appointmentRepository;
        this.billingStrategy = billingStrategy;
        this.appointmentEventPublisher = appointmentEventPublisher;
    }

    @Override
    public Appointment save(Appointment appointment) {
        appointment.setBillAmount(billingStrategy.calculate(appointment));
        Appointment created = appointmentRepository.create(appointment);
        appointmentEventPublisher.publishCreated(created);
        return created;
    }

    @Override
    public Appointment get(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found: " + id));
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
