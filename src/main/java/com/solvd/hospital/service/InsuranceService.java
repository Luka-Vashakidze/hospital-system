package com.solvd.hospital.service;

import com.solvd.hospital.domain.Insurance;

import java.util.List;

public interface InsuranceService {

    Insurance save(Insurance insurance);

    Insurance get(Long id);

    List<Insurance> getAll();

    List<Insurance> findExpiring();

    Insurance update(Insurance insurance);

    boolean remove(Long id);
}
