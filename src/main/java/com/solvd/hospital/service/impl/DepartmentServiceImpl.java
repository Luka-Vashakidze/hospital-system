package com.solvd.hospital.service.impl;

import com.solvd.hospital.domain.Department;
import com.solvd.hospital.persistence.DepartmentRepository;
import com.solvd.hospital.persistence.impl.DepartmentRepositoryImpl;
import com.solvd.hospital.service.DepartmentService;

import java.util.List;
import java.util.Optional;

public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl() {
        this.departmentRepository = new DepartmentRepositoryImpl();
    }

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Department save(Department department) {
        return departmentRepository.create(department);
    }

    @Override
    public Optional<Department> get(Long id) {
        return departmentRepository.findById(id);
    }

    @Override
    public List<Department> getAll() {
        return departmentRepository.findAll();
    }

    @Override
    public List<Department> findByHospital(Long hospitalId) {
        return departmentRepository.findByHospitalId(hospitalId);
    }

    @Override
    public Department update(Department department) {
        return departmentRepository.update(department);
    }

    @Override
    public boolean remove(Long id) {
        return departmentRepository.deleteById(id);
    }
}
