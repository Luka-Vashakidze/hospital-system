package com.solvd.hospital.service.impl;

import com.solvd.hospital.domain.Hospital;
import com.solvd.hospital.persistence.HospitalRepository;
import com.solvd.hospital.persistence.impl.HospitalRepositoryImpl;
import com.solvd.hospital.service.HospitalService;

import java.util.List;
import java.util.Optional;

public class HospitalServiceImpl implements HospitalService {
    private final HospitalRepository hospitalRepository;

    public HospitalServiceImpl() {
        this.hospitalRepository = new HospitalRepositoryImpl();
    }

    public HospitalServiceImpl(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    @Override
    public Hospital save(Hospital hospital) {
        return hospitalRepository.create(hospital);
    }

    @Override
    public Optional<Hospital> get(Long id) {
        return hospitalRepository.findById(id);
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
