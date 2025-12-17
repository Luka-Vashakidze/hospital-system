package com.solvd.hospital.service;

import com.solvd.hospital.domain.Hospital;

import java.util.List;

public interface HospitalService {

    Hospital save(Hospital hospital);

    Hospital get(Long id);

    List<Hospital> getAll();

    Hospital update(Hospital hospital);

    boolean remove(Long id);
}
