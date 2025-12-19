package com.solvd.hospital.patterns.notification;

import com.solvd.hospital.domain.Appointment;

public class BasicNotificationService implements NotificationService {

    @Override
    public void notifyAppointmentCreated(Appointment appointment) {
        System.out.println("Notification: appointment created with id " + appointment.getId());
    }
}
