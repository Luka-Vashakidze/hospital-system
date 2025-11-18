package com.solvd.hospital.service.impl;

import com.solvd.hospital.domain.Doctor;
import com.solvd.hospital.persistence.DoctorRepository;
import com.solvd.hospital.persistence.impl.DoctorRepositoryImpl;
import com.solvd.hospital.service.DoctorService;

import java.util.List;
import java.util.Optional;

public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;

    public DoctorServiceImpl() {
        this.doctorRepository = new DoctorRepositoryImpl();
    }

    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public Doctor save(Doctor doctor) {
        return doctorRepository.create(doctor);
    }

    @Override
    public Optional<Doctor> get(Long id) {
        return doctorRepository.findById(id);
    }

    @Override
    public List<Doctor> getAll() {
        return doctorRepository.findAll();
    }

    @Override
    public List<Doctor> findByDepartment(Long departmentId) {
        return doctorRepository.findByDepartmentId(departmentId);
    }

    @Override
    public Doctor update(Doctor doctor) {
        return doctorRepository.update(doctor);
    }

    @Override
    public boolean remove(Long id) {
        return doctorRepository.deleteById(id);
    }
}
