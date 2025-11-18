package com.solvd.hospital.persistence;

import com.solvd.hospital.domain.Patient;

import java.util.List;

public interface PatientRepository extends CrudRepository<Patient, Long> {
    List<Patient> findByInsurance(Long insuranceId);
}
