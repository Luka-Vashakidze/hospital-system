package com.solvd.hospital.patterns.notification;

import com.solvd.hospital.domain.Appointment;

public class LoggingNotificationDecorator implements NotificationService {

    private final NotificationService delegate;

    public LoggingNotificationDecorator(NotificationService delegate) {
        this.delegate = delegate;
    }

    @Override
    public void notifyAppointmentCreated(Appointment appointment) {
        System.out.println("Decorator: preparing notification for appointment " + appointment.getId());
        delegate.notifyAppointmentCreated(appointment);
        System.out.println("Decorator: notification sent for appointment " + appointment.getId());
    }
}
