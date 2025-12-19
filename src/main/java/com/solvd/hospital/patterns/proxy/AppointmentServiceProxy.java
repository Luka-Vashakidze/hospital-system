package com.solvd.hospital.patterns.proxy;

import com.solvd.hospital.domain.Appointment;
import com.solvd.hospital.domain.AppointmentDetail;
import com.solvd.hospital.service.AppointmentService;

import java.time.LocalDateTime;
import java.util.List;

public class AppointmentServiceProxy implements AppointmentService {

    private final AppointmentService delegate;
    private final boolean allowModifications;

    public AppointmentServiceProxy(AppointmentService delegate, boolean allowModifications) {
        this.delegate = delegate;
        this.allowModifications = allowModifications;
    }

    @Override
    public Appointment save(Appointment appointment) {
        ensureWriteAllowed();
        return delegate.save(appointment);
    }

    @Override
    public Appointment get(Long id) {
        return delegate.get(id);
    }

    @Override
    public List<Appointment> getAll() {
        return delegate.getAll();
    }

    @Override
    public List<Appointment> findByDoctor(Long doctorId) {
        return delegate.findByDoctor(doctorId);
    }

    @Override
    public List<Appointment> findByPatient(Long patientId) {
        return delegate.findByPatient(patientId);
    }

    @Override
    public List<AppointmentDetail> upcomingDetails(LocalDateTime fromDateTime) {
        return delegate.upcomingDetails(fromDateTime);
    }

    @Override
    public Appointment update(Appointment appointment) {
        ensureWriteAllowed();
        return delegate.update(appointment);
    }

    @Override
    public boolean remove(Long id) {
        ensureWriteAllowed();
        return delegate.remove(id);
    }

    private void ensureWriteAllowed() {
        if (!allowModifications) {
            throw new IllegalStateException("Write operations are not permitted in the current context");
        }
    }
}
