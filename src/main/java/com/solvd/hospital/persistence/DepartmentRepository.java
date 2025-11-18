package com.solvd.hospital.persistence;

import com.solvd.hospital.domain.Department;

import java.util.List;

public interface DepartmentRepository extends CrudRepository<Department, Long> {
    List<Department> findByHospitalId(Long hospitalId);
}
