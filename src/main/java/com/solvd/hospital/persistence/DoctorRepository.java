package com.solvd.hospital.persistence;

import com.solvd.hospital.domain.Doctor;

import java.util.List;

public interface DoctorRepository extends CrudRepository<Doctor, Long> {
    List<Doctor> findByDepartmentId(Long departmentId);
}
