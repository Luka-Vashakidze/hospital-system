package com.solvd.hospital.service;

import com.solvd.hospital.domain.Doctor;

import java.util.List;
import java.util.Optional;

public interface DoctorService {
    Doctor save(Doctor doctor);

    Optional<Doctor> get(Long id);

    List<Doctor> getAll();

    List<Doctor> findByDepartment(Long departmentId);

    Doctor update(Doctor doctor);

    boolean remove(Long id);
}
