package com.solvd.hospital.persistence.mybatis.impl;

import com.solvd.hospital.domain.Hospital;
import com.solvd.hospital.persistence.HospitalRepository;
import com.solvd.hospital.persistence.mybatis.HospitalMapper;
import com.solvd.hospital.persistence.mybatis.MyBatisConfig;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;
import java.util.Optional;

public class HospitalRepositoryMyBatisImpl implements HospitalRepository {

    private final SqlSessionFactory sqlSessionFactory;

    public HospitalRepositoryMyBatisImpl() {
        this(MyBatisConfig.getSqlSessionFactory());
    }

    public HospitalRepositoryMyBatisImpl(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public Hospital create(Hospital hospital) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            HospitalMapper mapper = session.getMapper(HospitalMapper.class);
            mapper.insert(hospital);
        }
        return hospital;
    }

    @Override
    public Optional<Hospital> findById(Long id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            HospitalMapper mapper = session.getMapper(HospitalMapper.class);
            return Optional.ofNullable(mapper.selectByIdWithDepartments(id));
        }
    }

    @Override
    public List<Hospital> findAll() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            HospitalMapper mapper = session.getMapper(HospitalMapper.class);
            return mapper.selectAll();
        }
    }

    @Override
    public Hospital update(Hospital hospital) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            HospitalMapper mapper = session.getMapper(HospitalMapper.class);
            mapper.update(hospital);
        }
        return hospital;
    }

    @Override
    public boolean deleteById(Long id) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            HospitalMapper mapper = session.getMapper(HospitalMapper.class);
            return mapper.delete(id) > 0;
        }
    }
}
