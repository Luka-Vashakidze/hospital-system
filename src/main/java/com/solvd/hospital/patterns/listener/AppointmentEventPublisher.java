package com.solvd.hospital.patterns.listener;

import com.solvd.hospital.domain.Appointment;

import java.util.ArrayList;
import java.util.List;

public class AppointmentEventPublisher {

    private final List<AppointmentListener> listeners = new ArrayList<>();

    public void register(AppointmentListener listener) {
        listeners.add(listener);
    }

    public void unregister(AppointmentListener listener) {
        listeners.remove(listener);
    }

    public void publishCreated(Appointment appointment) {
        listeners.forEach(listener -> listener.onCreated(appointment));
    }
}
