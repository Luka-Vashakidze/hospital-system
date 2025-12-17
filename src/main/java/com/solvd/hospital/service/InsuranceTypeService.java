package com.solvd.hospital.service;

import com.solvd.hospital.domain.InsuranceType;

import java.util.List;

public interface InsuranceTypeService {

    InsuranceType save(InsuranceType insuranceType);

    InsuranceType get(Long id);

    List<InsuranceType> getAll();

    InsuranceType update(InsuranceType insuranceType);

    boolean remove(Long id);
}
