package com.solvd.hospital.patterns.facade;

import com.solvd.hospital.domain.Appointment;
import com.solvd.hospital.domain.Hospital;
import com.solvd.hospital.domain.builder.AppointmentBuilder;
import com.solvd.hospital.patterns.factory.ServiceFactory;
import com.solvd.hospital.patterns.listener.AppointmentEventPublisher;
import com.solvd.hospital.patterns.listener.NotificationAppointmentListener;
import com.solvd.hospital.patterns.notification.NotificationService;
import com.solvd.hospital.patterns.proxy.AppointmentServiceProxy;
import com.solvd.hospital.patterns.strategy.BillingStrategy;
import com.solvd.hospital.service.AppointmentService;
import com.solvd.hospital.service.DepartmentService;
import com.solvd.hospital.service.DoctorService;
import com.solvd.hospital.service.HospitalService;
import com.solvd.hospital.service.PatientService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class HospitalFacade {

    private final HospitalService hospitalService;
    private final DepartmentService departmentService;
    private final DoctorService doctorService;
    private final PatientService patientService;
    private final AppointmentService appointmentServiceProxy;

    public HospitalFacade(ServiceFactory serviceFactory,
                          BillingStrategy billingStrategy,
                          AppointmentEventPublisher appointmentEventPublisher,
                          NotificationService notificationService,
                          boolean allowAppointments) {
        this.hospitalService = serviceFactory.hospitalService();
        this.departmentService = serviceFactory.departmentService();
        this.doctorService = serviceFactory.doctorService();
        this.patientService = serviceFactory.patientService();
        AppointmentService appointmentService = serviceFactory.appointmentService(billingStrategy, appointmentEventPublisher);
        appointmentEventPublisher.register(new NotificationAppointmentListener(notificationService));
        this.appointmentServiceProxy = new AppointmentServiceProxy(appointmentService, allowAppointments);
    }

    public Hospital registerHospital(Hospital hospital) {
        return hospitalService.save(hospital);
    }

    public Appointment scheduleAppointment(Long departmentId,
                                           Long doctorId,
                                           Long patientId,
                                           LocalDateTime dateTime,
                                           String purpose,
                                           BigDecimal billAmount) {
        Appointment appointment = new AppointmentBuilder()
                .withDepartmentId(departmentId)
                .withDoctorId(doctorId)
                .withPatientId(patientId)
                .withDateTime(dateTime)
                .withPurpose(purpose)
                .withStatus("NEW")
                .withBillAmount(billAmount)
                .build();
        return appointmentServiceProxy.save(appointment);
    }

    public HospitalService getHospitalService() {
        return hospitalService;
    }

    public DepartmentService getDepartmentService() {
        return departmentService;
    }

    public DoctorService getDoctorService() {
        return doctorService;
    }

    public PatientService getPatientService() {
        return patientService;
    }

    public AppointmentService getAppointmentService() {
        return appointmentServiceProxy;
    }
}
