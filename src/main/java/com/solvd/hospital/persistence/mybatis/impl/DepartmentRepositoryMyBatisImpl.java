package com.solvd.hospital.persistence.mybatis.impl;

import com.solvd.hospital.domain.Department;
import com.solvd.hospital.persistence.DepartmentRepository;
import com.solvd.hospital.persistence.mybatis.DepartmentMapper;
import com.solvd.hospital.persistence.mybatis.MyBatisConfig;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;
import java.util.Optional;

public class DepartmentRepositoryMyBatisImpl implements DepartmentRepository {

    private final SqlSessionFactory sqlSessionFactory;

    public DepartmentRepositoryMyBatisImpl() {
        this(MyBatisConfig.getSqlSessionFactory());
    }

    public DepartmentRepositoryMyBatisImpl(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public Department create(Department department) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            DepartmentMapper mapper = session.getMapper(DepartmentMapper.class);
            mapper.insert(department);
        }
        return department;
    }

    @Override
    public Optional<Department> findById(Long id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            DepartmentMapper mapper = session.getMapper(DepartmentMapper.class);
            return Optional.ofNullable(mapper.selectById(id));
        }
    }

    @Override
    public List<Department> findAll() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            DepartmentMapper mapper = session.getMapper(DepartmentMapper.class);
            return mapper.selectAll();
        }
    }

    @Override
    public List<Department> findByHospitalId(Long hospitalId) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            DepartmentMapper mapper = session.getMapper(DepartmentMapper.class);
            return mapper.selectByHospitalId(hospitalId);
        }
    }

    @Override
    public Department update(Department department) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            DepartmentMapper mapper = session.getMapper(DepartmentMapper.class);
            mapper.update(department);
        }
        return department;
    }

    @Override
    public boolean deleteById(Long id) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            DepartmentMapper mapper = session.getMapper(DepartmentMapper.class);
            return mapper.delete(id) > 0;
        }
    }
}
