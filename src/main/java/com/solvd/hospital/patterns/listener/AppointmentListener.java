package com.solvd.hospital.patterns.listener;

import com.solvd.hospital.domain.Appointment;

public interface AppointmentListener {
    void onCreated(Appointment appointment);
}
