package com.solvd.hospital.persistence;

import com.solvd.hospital.domain.Insurance;

import java.util.List;

public interface InsuranceRepository extends CrudRepository<Insurance, Long> {
    List<Insurance> findExpiringPolicies();
}
