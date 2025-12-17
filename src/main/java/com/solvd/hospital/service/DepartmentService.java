package com.solvd.hospital.service;

import com.solvd.hospital.domain.Department;

import java.util.List;

public interface DepartmentService {

    Department save(Department department);

    Department get(Long id);

    List<Department> getAll();

    List<Department> findByHospital(Long hospitalId);

    Department update(Department department);

    boolean remove(Long id);
}
