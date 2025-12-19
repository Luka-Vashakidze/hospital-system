package com.solvd.hospital.patterns.notification;

import com.solvd.hospital.domain.Appointment;

public interface NotificationService {
    void notifyAppointmentCreated(Appointment appointment);
}
