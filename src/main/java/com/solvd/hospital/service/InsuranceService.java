package com.solvd.hospital.service;

import com.solvd.hospital.domain.Insurance;

import java.util.List;
import java.util.Optional;

public interface InsuranceService {
    Insurance save(Insurance insurance);

    Optional<Insurance> get(Long id);

    List<Insurance> getAll();

    List<Insurance> findExpiring();

    Insurance update(Insurance insurance);

    boolean remove(Long id);
}

