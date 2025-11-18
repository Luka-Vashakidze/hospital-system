package com.solvd.hospital.service.impl;

import com.solvd.hospital.domain.Insurance;
import com.solvd.hospital.persistence.InsuranceRepository;
import com.solvd.hospital.persistence.impl.InsuranceRepositoryImpl;
import com.solvd.hospital.service.InsuranceService;

import java.util.List;
import java.util.Optional;

public class InsuranceServiceImpl implements InsuranceService {
    private final InsuranceRepository insuranceRepository;

    public InsuranceServiceImpl() {
        this.insuranceRepository = new InsuranceRepositoryImpl();
    }

    public InsuranceServiceImpl(InsuranceRepository insuranceRepository) {
        this.insuranceRepository = insuranceRepository;
    }

    @Override
    public Insurance save(Insurance insurance) {
        return insuranceRepository.create(insurance);
    }

    @Override
    public Optional<Insurance> get(Long id) {
        return insuranceRepository.findById(id);
    }

    @Override
    public List<Insurance> getAll() {
        return insuranceRepository.findAll();
    }

    @Override
    public List<Insurance> findExpiring() {
        return insuranceRepository.findExpiringPolicies();
    }

    @Override
    public Insurance update(Insurance insurance) {
        return insuranceRepository.update(insurance);
    }

    @Override
    public boolean remove(Long id) {
        return insuranceRepository.deleteById(id);
    }
}

