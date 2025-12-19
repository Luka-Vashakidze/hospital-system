package com.solvd.hospital.persistence.mybatis.impl;

import com.solvd.hospital.domain.Patient;
import com.solvd.hospital.persistence.PatientRepository;
import com.solvd.hospital.persistence.mybatis.MyBatisConfig;
import com.solvd.hospital.persistence.mybatis.PatientMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;
import java.util.Optional;

public class PatientRepositoryMyBatisImpl implements PatientRepository {

    private final SqlSessionFactory sqlSessionFactory;

    public PatientRepositoryMyBatisImpl() {
        this(MyBatisConfig.getSqlSessionFactory());
    }

    public PatientRepositoryMyBatisImpl(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public Patient create(Patient patient) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            PatientMapper mapper = session.getMapper(PatientMapper.class);
            mapper.insert(patient);
        }
        return patient;
    }

    @Override
    public Optional<Patient> findById(Long id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            PatientMapper mapper = session.getMapper(PatientMapper.class);
            return Optional.ofNullable(mapper.selectById(id));
        }
    }

    @Override
    public List<Patient> findAll() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            PatientMapper mapper = session.getMapper(PatientMapper.class);
            return mapper.selectAll();
        }
    }

    @Override
    public List<Patient> findByInsurance(Long insuranceId) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            PatientMapper mapper = session.getMapper(PatientMapper.class);
            return mapper.selectByInsurance(insuranceId);
        }
    }

    @Override
    public Patient update(Patient patient) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            PatientMapper mapper = session.getMapper(PatientMapper.class);
            mapper.update(patient);
        }
        return patient;
    }

    @Override
    public boolean deleteById(Long id) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            PatientMapper mapper = session.getMapper(PatientMapper.class);
            return mapper.delete(id) > 0;
        }
    }
}
