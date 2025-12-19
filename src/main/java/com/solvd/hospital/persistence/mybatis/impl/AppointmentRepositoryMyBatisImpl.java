package com.solvd.hospital.persistence.mybatis.impl;

import com.solvd.hospital.domain.Appointment;
import com.solvd.hospital.domain.AppointmentDetail;
import com.solvd.hospital.persistence.AppointmentRepository;
import com.solvd.hospital.persistence.mybatis.AppointmentMapper;
import com.solvd.hospital.persistence.mybatis.MyBatisConfig;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class AppointmentRepositoryMyBatisImpl implements AppointmentRepository {

    private final SqlSessionFactory sqlSessionFactory;

    public AppointmentRepositoryMyBatisImpl() {
        this(MyBatisConfig.getSqlSessionFactory());
    }

    public AppointmentRepositoryMyBatisImpl(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public Appointment create(Appointment appointment) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            AppointmentMapper mapper = session.getMapper(AppointmentMapper.class);
            mapper.insert(appointment);
        }
        return appointment;
    }

    @Override
    public Optional<Appointment> findById(Long id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            AppointmentMapper mapper = session.getMapper(AppointmentMapper.class);
            return Optional.ofNullable(mapper.selectById(id));
        }
    }

    @Override
    public List<Appointment> findAll() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            AppointmentMapper mapper = session.getMapper(AppointmentMapper.class);
            return mapper.selectAll();
        }
    }

    @Override
    public List<Appointment> findByDoctor(Long doctorId) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            AppointmentMapper mapper = session.getMapper(AppointmentMapper.class);
            return mapper.selectByDoctor(doctorId);
        }
    }

    @Override
    public List<Appointment> findByPatient(Long patientId) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            AppointmentMapper mapper = session.getMapper(AppointmentMapper.class);
            return mapper.selectByPatient(patientId);
        }
    }

    @Override
    public List<AppointmentDetail> findUpcomingDetails(LocalDateTime fromDateTime) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            AppointmentMapper mapper = session.getMapper(AppointmentMapper.class);
            return mapper.selectUpcomingDetails(fromDateTime);
        }
    }

    @Override
    public Appointment update(Appointment appointment) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            AppointmentMapper mapper = session.getMapper(AppointmentMapper.class);
            mapper.update(appointment);
        }
        return appointment;
    }

    @Override
    public boolean deleteById(Long id) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            AppointmentMapper mapper = session.getMapper(AppointmentMapper.class);
            return mapper.delete(id) > 0;
        }
    }
}
