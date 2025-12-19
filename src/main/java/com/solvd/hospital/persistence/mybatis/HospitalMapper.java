package com.solvd.hospital.persistence.mybatis;

import com.solvd.hospital.domain.Hospital;

import java.util.List;

public interface HospitalMapper {

    void insert(Hospital hospital);

    Hospital selectById(Long id);

    List<Hospital> selectAll();

    int update(Hospital hospital);

    int delete(Long id);
}
