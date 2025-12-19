package com.solvd.hospital.persistence.mybatis;

import com.solvd.hospital.domain.InsuranceType;

import java.util.List;

public interface InsuranceTypeMapper {

    void insert(InsuranceType insuranceType);

    InsuranceType selectById(Long id);

    List<InsuranceType> selectAll();

    int update(InsuranceType insuranceType);

    int delete(Long id);
}
