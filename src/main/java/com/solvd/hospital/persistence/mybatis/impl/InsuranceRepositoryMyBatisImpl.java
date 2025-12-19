package com.solvd.hospital.persistence.mybatis.impl;

import com.solvd.hospital.domain.Insurance;
import com.solvd.hospital.persistence.InsuranceRepository;
import com.solvd.hospital.persistence.mybatis.InsuranceMapper;
import com.solvd.hospital.persistence.mybatis.MyBatisConfig;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;
import java.util.Optional;

public class InsuranceRepositoryMyBatisImpl implements InsuranceRepository {

    private final SqlSessionFactory sqlSessionFactory;

    public InsuranceRepositoryMyBatisImpl() {
        this(MyBatisConfig.getSqlSessionFactory());
    }

    public InsuranceRepositoryMyBatisImpl(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public Insurance create(Insurance insurance) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            InsuranceMapper mapper = session.getMapper(InsuranceMapper.class);
            mapper.insert(insurance);
        }
        return insurance;
    }

    @Override
    public Optional<Insurance> findById(Long id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            InsuranceMapper mapper = session.getMapper(InsuranceMapper.class);
            return Optional.ofNullable(mapper.selectById(id));
        }
    }

    @Override
    public List<Insurance> findAll() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            InsuranceMapper mapper = session.getMapper(InsuranceMapper.class);
            return mapper.selectAll();
        }
    }

    @Override
    public List<Insurance> findExpiringPolicies() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            InsuranceMapper mapper = session.getMapper(InsuranceMapper.class);
            return mapper.selectExpiringPolicies();
        }
    }

    @Override
    public Insurance update(Insurance insurance) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            InsuranceMapper mapper = session.getMapper(InsuranceMapper.class);
            mapper.update(insurance);
        }
        return insurance;
    }

    @Override
    public boolean deleteById(Long id) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            InsuranceMapper mapper = session.getMapper(InsuranceMapper.class);
            return mapper.delete(id) > 0;
        }
    }
}
