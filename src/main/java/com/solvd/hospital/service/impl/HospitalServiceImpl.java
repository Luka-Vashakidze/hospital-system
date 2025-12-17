package com.solvd.hospital.service.impl;

import com.solvd.hospital.domain.Hospital;
import com.solvd.hospital.persistence.DepartmentRepository;
import com.solvd.hospital.persistence.HospitalRepository;
import com.solvd.hospital.persistence.impl.DepartmentRepositoryImpl;
import com.solvd.hospital.persistence.impl.HospitalRepositoryImpl;
import com.solvd.hospital.service.HospitalService;

import java.util.List;

public class HospitalServiceImpl implements HospitalService {

    private final HospitalRepository hospitalRepository;
    private final DepartmentRepository departmentRepository;

    public HospitalServiceImpl() {
        this(new HospitalRepositoryImpl(), new DepartmentRepositoryImpl());
    }

    public HospitalServiceImpl(HospitalRepository hospitalRepository, DepartmentRepository departmentRepository) {
        this.hospitalRepository = hospitalRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Hospital save(Hospital hospital) {
        Hospital createdHospital = hospitalRepository.create(hospital);
        if (createdHospital.getDepartments() == null || createdHospital.getDepartments().isEmpty()) {
            return createdHospital;
        }

        createdHospital.getDepartments().forEach(department -> {
            department.setHospitalId(createdHospital.getId());
            departmentRepository.create(department);
        });
        return createdHospital;
    }

    @Override
    public Hospital get(Long id) {
        return hospitalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Hospital not found: " + id));
    }

    @Override
    public List<Hospital> getAll() {
        return hospitalRepository.findAll();
    }

    @Override
    public Hospital update(Hospital hospital) {
        return hospitalRepository.update(hospital);
    }

    @Override
    public boolean remove(Long id) {
        return hospitalRepository.deleteById(id);
    }
}
