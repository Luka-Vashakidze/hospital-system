package com.solvd.hospital.persistence.mybatis.impl;

import com.solvd.hospital.domain.InsuranceType;
import com.solvd.hospital.persistence.InsuranceTypeRepository;
import com.solvd.hospital.persistence.mybatis.InsuranceTypeMapper;
import com.solvd.hospital.persistence.mybatis.MyBatisConfig;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;
import java.util.Optional;

public class InsuranceTypeRepositoryMyBatisImpl implements InsuranceTypeRepository {

    private final SqlSessionFactory sqlSessionFactory;

    public InsuranceTypeRepositoryMyBatisImpl() {
        this(MyBatisConfig.getSqlSessionFactory());
    }

    public InsuranceTypeRepositoryMyBatisImpl(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public InsuranceType create(InsuranceType insuranceType) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            InsuranceTypeMapper mapper = session.getMapper(InsuranceTypeMapper.class);
            mapper.insert(insuranceType);
        }
        return insuranceType;
    }

    @Override
    public Optional<InsuranceType> findById(Long id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            InsuranceTypeMapper mapper = session.getMapper(InsuranceTypeMapper.class);
            return Optional.ofNullable(mapper.selectById(id));
        }
    }

    @Override
    public List<InsuranceType> findAll() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            InsuranceTypeMapper mapper = session.getMapper(InsuranceTypeMapper.class);
            return mapper.selectAll();
        }
    }

    @Override
    public InsuranceType update(InsuranceType insuranceType) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            InsuranceTypeMapper mapper = session.getMapper(InsuranceTypeMapper.class);
            mapper.update(insuranceType);
        }
        return insuranceType;
    }

    @Override
    public boolean deleteById(Long id) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            InsuranceTypeMapper mapper = session.getMapper(InsuranceTypeMapper.class);
            return mapper.delete(id) > 0;
        }
    }
}
