package com.solvd.hospital.service;

import com.solvd.hospital.domain.Doctor;

import java.util.List;

public interface DoctorService {

    Doctor save(Doctor doctor);

    Doctor get(Long id);

    List<Doctor> getAll();

    List<Doctor> findByDepartment(Long departmentId);

    Doctor update(Doctor doctor);

    boolean remove(Long id);
}
