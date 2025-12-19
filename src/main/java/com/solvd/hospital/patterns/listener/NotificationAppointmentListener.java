package com.solvd.hospital.patterns.listener;

import com.solvd.hospital.domain.Appointment;
import com.solvd.hospital.patterns.notification.NotificationService;

public class NotificationAppointmentListener implements AppointmentListener {

    private final NotificationService notificationService;

    public NotificationAppointmentListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public void onCreated(Appointment appointment) {
        notificationService.notifyAppointmentCreated(appointment);
    }
}
