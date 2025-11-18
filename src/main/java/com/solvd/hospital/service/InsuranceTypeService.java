package com.solvd.hospital.service;

import com.solvd.hospital.domain.InsuranceType;

import java.util.List;
import java.util.Optional;

public interface InsuranceTypeService {
    InsuranceType save(InsuranceType insuranceType);

    Optional<InsuranceType> get(Long id);

    List<InsuranceType> getAll();

    InsuranceType update(InsuranceType insuranceType);

    boolean remove(Long id);
}

