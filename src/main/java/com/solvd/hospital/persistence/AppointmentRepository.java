package com.solvd.hospital.persistence;

import com.solvd.hospital.domain.Appointment;
import com.solvd.hospital.domain.AppointmentDetail;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends CrudRepository<Appointment, Long> {
    List<Appointment> findByDoctor(Long doctorId);

    List<Appointment> findByPatient(Long patientId);

    List<AppointmentDetail> findUpcomingDetails(LocalDateTime fromDateTime);
}
