package com.solvd.hospital.persistence.mybatis;

import com.solvd.hospital.domain.Doctor;

import java.util.List;

public interface DoctorMapper {

    void insert(Doctor doctor);

    Doctor selectById(Long id);

    List<Doctor> selectAll();

    List<Doctor> selectByDepartmentId(Long departmentId);

    int update(Doctor doctor);

    int delete(Long id);
}
