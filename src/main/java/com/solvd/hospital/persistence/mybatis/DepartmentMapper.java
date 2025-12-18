package com.solvd.hospital.persistence.mybatis;

import com.solvd.hospital.domain.Department;

import java.util.List;

public interface DepartmentMapper {

    void insert(Department department);

    Department selectById(Long id);

    List<Department> selectAll();

    List<Department> selectByHospitalId(Long hospitalId);

    int update(Department department);

    int delete(Long id);
}
