package com.solvd.hospital.persistence.mybatis;

import com.solvd.hospital.domain.Insurance;

import java.util.List;

public interface InsuranceMapper {

    void insert(Insurance insurance);

    Insurance selectById(Long id);

    List<Insurance> selectAll();

    List<Insurance> selectExpiringPolicies();

    int update(Insurance insurance);

    int delete(Long id);
}
