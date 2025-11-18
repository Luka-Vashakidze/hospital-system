package com.solvd.hospital.service;

import com.solvd.hospital.domain.Hospital;

import java.util.List;
import java.util.Optional;

public interface HospitalService {
    Hospital save(Hospital hospital);

    Optional<Hospital> get(Long id);

    List<Hospital> getAll();

    Hospital update(Hospital hospital);

    boolean remove(Long id);
}
