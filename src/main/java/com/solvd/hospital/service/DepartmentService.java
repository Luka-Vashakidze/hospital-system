package com.solvd.hospital.service;

import com.solvd.hospital.domain.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    Department save(Department department);

    Optional<Department> get(Long id);

    List<Department> getAll();

    List<Department> findByHospital(Long hospitalId);

    Department update(Department department);

    boolean remove(Long id);
}
