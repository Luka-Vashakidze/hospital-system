package com.solvd.hospital.service.impl;

import com.solvd.hospital.domain.InsuranceType;
import com.solvd.hospital.persistence.InsuranceTypeRepository;
import com.solvd.hospital.persistence.mybatis.impl.InsuranceTypeRepositoryMyBatisImpl;
import com.solvd.hospital.service.InsuranceTypeService;

import java.util.List;
import java.util.Optional;

public class InsuranceTypeServiceImpl implements InsuranceTypeService {

    private final InsuranceTypeRepository insuranceTypeRepository;

    public InsuranceTypeServiceImpl() {
        this.insuranceTypeRepository = new InsuranceTypeRepositoryMyBatisImpl();
    }

    public InsuranceTypeServiceImpl(InsuranceTypeRepository insuranceTypeRepository) {
        this.insuranceTypeRepository = insuranceTypeRepository;
    }

    @Override
    public InsuranceType save(InsuranceType insuranceType) {
        return insuranceTypeRepository.create(insuranceType);
    }

    @Override
    public InsuranceType get(Long id) {
        return insuranceTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Insurance type not found: " + id));
    }

    @Override
    public List<InsuranceType> getAll() {
        return insuranceTypeRepository.findAll();
    }

    @Override
    public InsuranceType update(InsuranceType insuranceType) {
        return insuranceTypeRepository.update(insuranceType);
    }

    @Override
    public boolean remove(Long id) {
        return insuranceTypeRepository.deleteById(id);
    }
}
