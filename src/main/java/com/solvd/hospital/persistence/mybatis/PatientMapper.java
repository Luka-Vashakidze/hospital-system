package com.solvd.hospital.persistence.mybatis;

import com.solvd.hospital.domain.Patient;

import java.util.List;

public interface PatientMapper {

    void insert(Patient patient);

    Patient selectById(Long id);

    List<Patient> selectAll();

    List<Patient> selectByInsurance(Long insuranceId);

    int update(Patient patient);

    int delete(Long id);
}
