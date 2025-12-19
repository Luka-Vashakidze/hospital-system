package com.solvd.hospital.service.impl;

import com.solvd.hospital.domain.Hospital;
import com.solvd.hospital.persistence.DepartmentRepository;
import com.solvd.hospital.persistence.HospitalRepository;
import com.solvd.hospital.persistence.mybatis.impl.HospitalRepositoryMyBatisImpl;
import com.solvd.hospital.service.HospitalService;
import com.solvd.hospital.service.DepartmentService;
import com.solvd.hospital.service.impl.DepartmentServiceImpl;

import java.util.List;
import java.util.Optional;

public class HospitalServiceImpl implements HospitalService {

    private final HospitalRepository hospitalRepository;
    private final DepartmentService departmentService;

    public HospitalServiceImpl() {
        this(new HospitalRepositoryMyBatisImpl(), new DepartmentServiceImpl());
}

    public HospitalServiceImpl(HospitalRepository hospitalRepository, DepartmentService departmentService) {
        this.hospitalRepository = hospitalRepository;
        this.departmentService = departmentService;
    }

    @Override
    public Hospital save(Hospital hospital) {
        Hospital createdHospital = hospitalRepository.create(hospital);
        if (createdHospital.getDepartments() == null || createdHospital.getDepartments().isEmpty()) {
            return createdHospital;
        }

        createdHospital.getDepartments().forEach(department -> {
            department.setHospitalId(createdHospital.getId());
            departmentService.save(department);
        });
        return createdHospital;
    }

    @Override
    public Hospital get(Long id) {
        return hospitalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("hspital not found: " + id));
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