package com.solvd.hospital.service;

import com.solvd.hospital.domain.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientService {
    Patient save(Patient patient);

    Optional<Patient> get(Long id);

    List<Patient> getAll();

    List<Patient> findByInsurance(Long insuranceId);

    Patient update(Patient patient);

    boolean remove(Long id);
}
