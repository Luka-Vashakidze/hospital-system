package com.solvd.hospital.service.impl;

import com.solvd.hospital.domain.Patient;
import com.solvd.hospital.persistence.PatientRepository;
import com.solvd.hospital.persistence.impl.PatientRepositoryImpl;
import com.solvd.hospital.service.PatientService;

import java.util.List;
import java.util.Optional;

public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;

    public PatientServiceImpl() {
        this.patientRepository = new PatientRepositoryImpl();
    }

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public Patient save(Patient patient) {
        return patientRepository.create(patient);
    }

    @Override
    public Optional<Patient> get(Long id) {
        return patientRepository.findById(id);
    }

    @Override
    public List<Patient> getAll() {
        return patientRepository.findAll();
    }

    @Override
    public List<Patient> findByInsurance(Long insuranceId) {
        return patientRepository.findByInsurance(insuranceId);
    }

    @Override
    public Patient update(Patient patient) {
        return patientRepository.update(patient);
    }

    @Override
    public boolean remove(Long id) {
        return patientRepository.deleteById(id);
    }
}
