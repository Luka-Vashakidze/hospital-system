package com.solvd.hospital.persistence.mybatis.impl;

import com.solvd.hospital.domain.Doctor;
import com.solvd.hospital.persistence.DoctorRepository;
import com.solvd.hospital.persistence.mybatis.DoctorMapper;
import com.solvd.hospital.persistence.mybatis.MyBatisConfig;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;
import java.util.Optional;

public class DoctorRepositoryMyBatisImpl implements DoctorRepository {

    private final SqlSessionFactory sqlSessionFactory;

    public DoctorRepositoryMyBatisImpl() {
        this(MyBatisConfig.getSqlSessionFactory());
    }

    public DoctorRepositoryMyBatisImpl(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public Doctor create(Doctor doctor) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            DoctorMapper mapper = session.getMapper(DoctorMapper.class);
            mapper.insert(doctor);
        }
        return doctor;
    }

    @Override
    public Optional<Doctor> findById(Long id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            DoctorMapper mapper = session.getMapper(DoctorMapper.class);
            return Optional.ofNullable(mapper.selectById(id));
        }
    }

    @Override
    public List<Doctor> findAll() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            DoctorMapper mapper = session.getMapper(DoctorMapper.class);
            return mapper.selectAll();
        }
    }

    @Override
    public List<Doctor> findByDepartmentId(Long departmentId) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            DoctorMapper mapper = session.getMapper(DoctorMapper.class);
            return mapper.selectByDepartmentId(departmentId);
        }
    }

    @Override
    public Doctor update(Doctor doctor) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            DoctorMapper mapper = session.getMapper(DoctorMapper.class);
            mapper.update(doctor);
        }
        return doctor;
    }

    @Override
    public boolean deleteById(Long id) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            DoctorMapper mapper = session.getMapper(DoctorMapper.class);
            return mapper.delete(id) > 0;
        }
    }
}
