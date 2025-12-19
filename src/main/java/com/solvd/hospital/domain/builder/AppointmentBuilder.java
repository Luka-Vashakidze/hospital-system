package com.solvd.hospital.domain.builder;

import com.solvd.hospital.domain.Appointment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AppointmentBuilder {

    private Long departmentId;
    private Long doctorId;
    private Long patientId;
    private LocalDateTime dateTime;
    private String purpose;
    private String status;
    private BigDecimal billAmount;

    public AppointmentBuilder withDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
        return this;
    }

    public AppointmentBuilder withDoctorId(Long doctorId) {
        this.doctorId = doctorId;
        return this;
    }

    public AppointmentBuilder withPatientId(Long patientId) {
        this.patientId = patientId;
        return this;
    }

    public AppointmentBuilder withDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public AppointmentBuilder withPurpose(String purpose) {
        this.purpose = purpose;
        return this;
    }

    public AppointmentBuilder withStatus(String status) {
        this.status = status;
        return this;
    }

    public AppointmentBuilder withBillAmount(BigDecimal billAmount) {
        this.billAmount = billAmount;
        return this;
    }

    public Appointment build() {
        Appointment appointment = new Appointment();
        appointment.setDepartmentId(departmentId);
        appointment.setDoctorId(doctorId);
        appointment.setPatientId(patientId);
        appointment.setDateTime(dateTime);
        appointment.setPurpose(purpose);
        appointment.setStatus(status);
        appointment.setBillAmount(billAmount);
        return appointment;
    }
}
