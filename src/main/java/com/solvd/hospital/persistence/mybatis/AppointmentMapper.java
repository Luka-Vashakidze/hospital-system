package com.solvd.hospital.persistence.mybatis;

import com.solvd.hospital.domain.Appointment;
import com.solvd.hospital.domain.AppointmentDetail;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface AppointmentMapper {

    void insert(Appointment appointment);

    Appointment selectById(Long id);

    List<Appointment> selectAll();

    List<Appointment> selectByDoctor(Long doctorId);

    List<Appointment> selectByPatient(Long patientId);

    List<AppointmentDetail> selectUpcomingDetails(@Param("fromDateTime") LocalDateTime fromDateTime);

    int update(Appointment appointment);

    int delete(Long id);
}
